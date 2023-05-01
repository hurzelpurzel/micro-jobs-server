package com.andreidodu.service.impl;

import com.andreidodu.constants.ApplicationConst;
import com.andreidodu.constants.JobConst;
import com.andreidodu.dto.JobDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.mapper.JobMapper;
import com.andreidodu.model.Job;
import com.andreidodu.model.JobPicture;
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
import org.springframework.web.context.annotation.ApplicationScope;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(Transactional.TxType.REQUIRED)
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final JobPageableRepository jobPageableRepository;
    private final JobPictureRepository jobPictureRepository;
    private final JobMapper jobMapper;

    @Override
    public JobDTO get(Long id) throws ApplicationException {
        Optional<Job> modelOpt = this.jobRepository.findById(id);
        if (modelOpt.isEmpty()) {
            throw new ApplicationException("Job not found");
        }
        Job job = modelOpt.get();
        JobDTO dto = this.jobMapper.toDTO(job);
        dto.setPictureNamesList(transformJobPictureListToStringList(job.getJobPictureList()));
        return dto;
    }

    private List<String> transformJobPictureListToStringList(List<JobPicture> jobPictureList) {
        return jobPictureList.stream().map(jobPicture ->
                jobPicture.getPictureName()
        ).collect(Collectors.toList());
    }

    @Override
    public List<JobDTO> getAll(int type, int page) throws ApplicationException {
        JobDTOValidator.validateJobType(type);
        Pageable secondPageWithFiveElements = PageRequest.of(page, 10);
        List<Job> models = this.jobPageableRepository.findByType(type, secondPageWithFiveElements);
        return this.jobMapper.toListDTO(models);
    }

    @Override
    public List<JobDTO> getAll(String username, int type, int page) throws ApplicationException {
        JobDTOValidator.validateJobType(type);
        Pageable secondPageWithFiveElements = PageRequest.of(page, 10);
        List<Job> models = this.jobPageableRepository.findByTypeAndPublisher_username(type, username, secondPageWithFiveElements);
        return this.jobMapper.toListDTO(models);
    }

    @Override
    public void delete(Long jobId, String username) throws ApplicationException {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new ApplicationException("User not found"));
        Job job = this.jobRepository.findById(jobId)
                .orElseThrow(() -> new ApplicationException("Job does not exists"));
        // TODO add also the administrator here
        if (!user.getUsername().equals(job.getPublisher().getUsername())) {
            throw new ApplicationException("You are nto allowed to do this operation");
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
        Optional<User> userOpt = this.userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new ApplicationException("User not found");
        }
        jobDTO.setStatus(JobConst.STATUS_CREATED);
        Job model = this.jobMapper.toModel(jobDTO);
        model.setPublisher(userOpt.get());
        final Job job = this.jobRepository.save(model);
        saveJobPictureModelList(jobDTO.getImagesContent(), job);
        return this.jobMapper.toDTO(job);
    }


    private void saveJobPictureModelList(final List<String> jobPictureStringList, Job job) {
        Optional.ofNullable(jobPictureStringList)
                .map(list -> list.stream().map(base64ImageFull -> {
                    try {
                        return base64ImageToJobPictureModel(job, base64ImageFull);
                    } catch (IOException | NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList())).orElse(new ArrayList<>())
                .forEach(modelJobPicture -> {
                    this.jobPictureRepository.save(modelJobPicture);
                });
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
    public JobDTO approveJob(Long jobId, String owner) throws ApplicationException {
        Optional<User> userOpt = this.userRepository.findByUsername(owner);
        if (userOpt.isEmpty()) {
            throw new ApplicationException("User not found");
        }
        Optional<Job> modelOpt = this.jobRepository.findById(jobId);
        if (modelOpt.isEmpty()) {
            throw new ApplicationException("Job not found");
        }
        modelOpt.get().setStatus(JobConst.STATUS_PUBLISHED);
        Job model = this.jobRepository.save(modelOpt.get());
        return this.jobMapper.toDTO(model);
    }

    @Override
    public JobDTO update(Long id, JobDTO jobDTO, String owner) throws ApplicationException {
        if (!id.equals(jobDTO.getId())) {
            throw new ApplicationException("id not matching");
        }
        Optional<Job> userOpt = this.jobRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new ApplicationException("job not found");
        }
        Job job = userOpt.get();
        if (!job.getPublisher().getUsername().equals(owner)) {
            throw new ApplicationException("wrong user");
        }
        this.jobMapper.getModelMapper().map(jobDTO, job);
        Job userSaved = this.jobRepository.save(job);
        return this.jobMapper.toDTO(userSaved);

    }

    @Override
    public long countByType(int type) {
        return this.jobRepository.countByType(type);
    }

    @Override
    public long countByTypeAndUsername(String username, int type) {
        return this.jobRepository.countByTypeAndPublisher_username(type, username);
    }

}
