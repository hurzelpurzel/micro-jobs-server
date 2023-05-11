package com.andreidodu.service.impl;

import com.andreidodu.constants.ApplicationConst;
import com.andreidodu.constants.JobConst;
import com.andreidodu.dto.JobDTO;
import com.andreidodu.dto.JobPictureDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.exception.ValidationException;
import com.andreidodu.mapper.JobMapper;
import com.andreidodu.model.Job;
import com.andreidodu.model.JobPicture;
import com.andreidodu.model.Role;
import com.andreidodu.model.User;
import com.andreidodu.repository.JobPageableRepository;
import com.andreidodu.repository.JobPictureRepository;
import com.andreidodu.repository.JobRepository;
import com.andreidodu.repository.UserRepository;
import com.andreidodu.service.JobService;
import com.andreidodu.validators.JobDTOValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(Transactional.TxType.REQUIRED)
public class JobServiceImpl implements JobService {
    private static Integer MAX_NUMBER_ATTACHED_PICTURES = 5;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final JobPageableRepository jobPageableRepository;
    private final JobPictureRepository jobPictureRepository;
    private final JobMapper jobMapper;

    @Override
    public JobDTO getPrivate(final Long id, final String username) throws ApplicationException {
        Job job = this.jobRepository.findById(id).orElseThrow(() -> new ApplicationException("Job not found"));
        User publisher = job.getPublisher();
        if (!publisher.getUsername().equals(username)) {
            throw new ApplicationException("User not match");
        }
        return this.jobMapper.toDTO(job);
    }

    @Override
    public JobDTO getPublic(Long id) throws ApplicationException {
        Job job = this.jobRepository.findByIdAndStatus(id, JobConst.STATUS_PUBLISHED).orElseThrow(() -> new ApplicationException("Job not found"));
        return this.jobMapper.toDTO(job);
    }

    @Override
    public JobDTO getPrivateByStatus(Long id, Integer jobStatus, String username) throws ApplicationException {
        User administrator = this.userRepository.findByUsername(username).orElseThrow(() -> new ApplicationException("User not found"));
        if (!administrator.getRole().equals(Role.ADMIN)) {
            throw new ValidationException("User is not admin");
        }
        Job job = this.jobRepository.findById(id).orElseThrow(() -> new ApplicationException("Job not found"));
        return this.jobMapper.toDTO(job);
    }

    @Override
    public List<JobDTO> getAllPublic(int type, int page) throws ApplicationException {
        JobDTOValidator.validateJobType(type);
        Pageable secondPageWithFiveElements = PageRequest.of(page, 10);
        List<Job> models = this.jobPageableRepository.findByTypeAndStatusIn(type, Arrays.asList(JobConst.STATUS_PUBLISHED), secondPageWithFiveElements);
        return this.jobMapper.toListDTO(models);
    }

    @Override
    public long countAllPublicByType(int type) {
        return this.jobRepository.countByTypeAndStatusIn(type, Arrays.asList(JobConst.STATUS_PUBLISHED));
    }

    @Override
    public List<JobDTO> getAllPrivate(String username, int type, int page) throws ApplicationException {
        JobDTOValidator.validateJobType(type);
        Pageable secondPageWithFiveElements = PageRequest.of(page, 10);
        List<Job> models = this.jobPageableRepository.findByTypeAndPublisher_username(type, username, secondPageWithFiveElements);
        return this.jobMapper.toListDTO(models);
    }

    @Override
    public long countAllPrivateByTypeAndUsername(String username, int type) {
        return this.jobRepository.countByTypeAndPublisher_username(type, username);
    }

    @Override
    public List<JobDTO> getAllPrivateByTypeAndStatus(int type, List<Integer> statuses, String username, int page) throws ApplicationException {
        JobDTOValidator.validateJobType(type);
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new ApplicationException("User not found"));
        if (!Role.ADMIN.equals(user.getRole())) {
            throw new ApplicationException("User is not admin");
        }
        Pageable pageable = PageRequest.of(page, 10);
        List<Job> models = this.jobPageableRepository.findByTypeAndStatusIn(type, statuses, pageable);
        return this.jobMapper.toListDTO(models);
    }

    @Override
    public long countAllPrivateByTypeAndStatus(int type, List<Integer> statuses, String username) throws ApplicationException {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new ApplicationException("User not found"));
        if (!Role.ADMIN.equals(user.getRole())) {
            throw new ApplicationException("User is not admin");
        }
        return this.jobRepository.countByTypeAndStatusIn(type, statuses);
    }

    @Override
    public void delete(Long jobId, String username) throws ApplicationException {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new ApplicationException("User not found"));
        Job job = this.jobRepository.findById(jobId).orElseThrow(() -> new ApplicationException("Job does not exists"));
        // TODO add also the administrator here
        if (!user.getUsername().equals(job.getPublisher().getUsername())) {
            throw new ValidationException("You are nto allowed to do this operation");
        }
        deleteFilesFromDisk(job.getJobPictureList());
        this.jobRepository.deleteByIdAndPublisher_Username(jobId, username);
    }

    private void deleteFilesFromDisk(List<JobPicture> jobPictureList) {
        jobPictureList.forEach(jobPicture -> {
            File file = new File(ApplicationConst.FILES_DIRECTORY + "/" + jobPicture.getPictureName());
            file.delete();
        });
    }

    @Override
    public JobDTO save(JobDTO jobDTO, String username) throws ApplicationException {
        if (jobDTO.getJobPictureList().size() > MAX_NUMBER_ATTACHED_PICTURES) {
            throw new ValidationException("Maximum number of pictures allowed is " + MAX_NUMBER_ATTACHED_PICTURES);
        }
        Optional<User> userOpt = this.userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new ApplicationException("User not found");
        }
        jobDTO.setStatus(JobConst.STATUS_CREATED);
        Job model = this.jobMapper.toModel(jobDTO);
        model.setPublisher(userOpt.get());
        final Job job = this.jobRepository.save(model);
        saveJobPictureModelList(jobDTO.getJobPictureList(), job);
        return this.jobMapper.toDTO(job);
    }

    private void saveJobPictureModelList(final List<JobPictureDTO> jobPictureDTOList, Job job) {
        if (jobPictureDTOList != null && jobPictureDTOList.size() > 0) {
            final List<JobPicture> listOfModels = jobPictureDTOList.stream()
                    .map(jobPictureDTO -> jobPictureDTO.getContent())
                    .map(base64ImageFull -> {
                        try {
                            return base64ImageToJobPictureModel(job, base64ImageFull);
                        } catch (IOException | NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        }
                    }).collect(Collectors.toList());
            if (listOfModels.size() > 0) {
                this.jobPictureRepository.saveAll(listOfModels);
            }
        }
    }

    private JobPicture base64ImageToJobPictureModel(Job job, String base64ImageFull) throws NoSuchAlgorithmException, IOException {
        final byte[] imageBytesData = convertBase64StringToBytes(base64ImageFull);
        final String fullFileName = calculateFileName(job, base64ImageFull, imageBytesData);
        writeImageOnFile(fullFileName, imageBytesData);
        return createJobPictureModel(fullFileName, job);
    }

    private byte[] convertBase64StringToBytes(final String base64String) throws UnsupportedEncodingException {
        final String dataSegment = base64String.substring(base64String.indexOf(",") + 1);
        byte[] byteData = dataSegment.getBytes("UTF-8");
        return Base64.getDecoder().decode(byteData);
    }

    private String calculateFileName(final Job job, final String base64ImageFull, final byte[] imageBytesData) throws NoSuchAlgorithmException, IOException {
        final byte[] signedImageBytesData = createNewArrayWithBytesAtTheEnd(imageBytesData, job.getId().toString().getBytes());
        final String bytesHashString = calculateBytesHashString(signedImageBytesData);
        final String fileExtension = calculateFileExtension(base64ImageFull);
        return bytesHashString + "." + fileExtension;
    }

    private byte[] createNewArrayWithBytesAtTheEnd(final byte[] target, final byte[] bytesToBeAdded) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.write(target);
        output.write(bytesToBeAdded);
        return output.toByteArray();
    }

    private String calculateBytesHashString(final byte[] data) throws NoSuchAlgorithmException {
        final byte[] hash = MessageDigest.getInstance("MD5").digest(data);
        return new BigInteger(1, hash).toString(16);
    }

    private String calculateFileExtension(final String base64ImageString) {
        return base64ImageString.substring("data:image/".length(), base64ImageString.indexOf(";base64"));
    }

    private void writeImageOnFile(final String fileName, final byte[] data) throws IOException {
        final String fullFilePath = ApplicationConst.FILES_DIRECTORY + "/" + fileName;
        FileOutputStream outputStream = new FileOutputStream(fullFilePath);
        outputStream.write(data);
        outputStream.close();
    }

    private JobPicture createJobPictureModel(final String fullFileName, final Job job) {
        JobPicture modelJobPicture = new JobPicture();
        modelJobPicture.setPictureName(fullFileName);
        modelJobPicture.setJob(job);
        return modelJobPicture;
    }

    @Override
    public JobDTO changeJobStatus(Long jobId, int jobStatus, String usernameAdministrator) throws ApplicationException {
        User administrator = this.userRepository.findByUsername(usernameAdministrator).orElseThrow(() -> new ApplicationException("User not found"));
        if (administrator.getRole() != Role.ADMIN) {
            throw new ApplicationException("User is not admin");
        }
        Job job = this.jobRepository.findById(jobId).orElseThrow(() -> new ApplicationException("Job not found"));
        job.setStatus(jobStatus);
        Job jobSaved = this.jobRepository.save(job);
        return this.jobMapper.toDTO(jobSaved);
    }

    @Override
    public JobDTO update(Long id, JobDTO jobDTO, String owner) throws ApplicationException {
        if (!id.equals(jobDTO.getId())) {
            throw new ValidationException("id not matching");
        }
        if (jobDTO.getJobPictureList().size() > MAX_NUMBER_ATTACHED_PICTURES) {
            throw new ValidationException("Maximum number of pictures allowed is " + MAX_NUMBER_ATTACHED_PICTURES);
        }
        Job job = this.jobRepository.findById(id).orElseThrow(() -> new ApplicationException("job not found"));
        if (!job.getPublisher().getUsername().equals(owner)) {
            throw new ApplicationException("wrong user");
        }
        this.jobMapper.getModelMapper().map(jobDTO, job);
        job.setStatus(JobConst.STATUS_UPDATED);
        Job jobSaved = this.jobRepository.save(job);
        deleteJobPicturesNotInDTOList(jobDTO, jobSaved);
        saveJobPicturesInDTOList(jobDTO, jobSaved);
        return this.jobMapper.toDTO(jobSaved);
    }

    private void saveJobPicturesInDTOList(JobDTO jobDTO, Job jobSaved) {
        List<JobPictureDTO> jobPictureDTOListToBeSaved = retrieveJobPictureDTOToBeSaved(jobDTO);
        saveJobPictureModelList(jobPictureDTOListToBeSaved, jobSaved);
    }

    private void deleteJobPicturesNotInDTOList(JobDTO jobDTO, Job jobSaved) {
        List<JobPicture> jobPictureList = jobSaved.getJobPictureList();
        List<String> jobPictureListOfNames = retrieveJobPictureListOfNamesFromDTO(jobDTO);
        List<JobPicture> jobPictureListToBeDeleted = calculateJobPictureListToBeDeleted(jobPictureList, jobPictureListOfNames);
        deletePicturesFromPictureList(jobPictureListToBeDeleted);
    }

    private static List<JobPictureDTO> retrieveJobPictureDTOToBeSaved(JobDTO jobDTO) {
        return jobDTO.getJobPictureList()
                .stream()
                .filter(jobPictureDTO -> jobPictureDTO.getContent() != null)
                .collect(Collectors.toList());
    }

    private void deletePicturesFromPictureList(List<JobPicture> jobPictureListToBeDeleted) {
        if (jobPictureListToBeDeleted.size() > 0) {
            deleteFilesFromDisk(jobPictureListToBeDeleted);
            this.jobPictureRepository.deleteAll(jobPictureListToBeDeleted);
        }
    }

    private static List<JobPicture> calculateJobPictureListToBeDeleted(List<JobPicture> jobPictureList, List<String> jobPictureListOfNames) {
        return jobPictureList.stream()
                .filter(jobPicture -> !jobPictureListOfNames.contains(jobPicture.getPictureName()))
                .collect(Collectors.toList());
    }

    private static List<String> retrieveJobPictureListOfNamesFromDTO(JobDTO jobDTO) {
        return jobDTO.getJobPictureList()
                .stream()
                .filter(jobPictureDTO -> jobPictureDTO.getContent() == null)
                .map(jobPicture -> jobPicture.getPictureName())
                .collect(Collectors.toList());
    }


}
