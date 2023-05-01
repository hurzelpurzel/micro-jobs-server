package com.andreidodu.controller;

import com.andreidodu.dto.GenericResponse;
import com.andreidodu.dto.JobDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.service.JobService;
import com.andreidodu.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/job/private")
@RequiredArgsConstructor
public class JobPrivateController {

    final private JobService jobService;

    final private JwtService jwtService;

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getPrivate(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws ApplicationException {
        return ResponseEntity.ok(this.jobService.getPrivate(id, jwtService.extractUsernameFromAuthorizzation(authorization)));
    }

    @PostMapping("/{id}")
    public ResponseEntity<JobDTO> approveJob(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationAdministrator) throws ApplicationException {
        return ResponseEntity.ok(this.jobService.approveJob(id, jwtService.extractUsernameFromAuthorizzation(authorizationAdministrator)));
    }

    @GetMapping("/{jobType}/{page}")
    public ResponseEntity<List<JobDTO>> getAllPrivatePaginated(@PathVariable Integer jobType, @PathVariable Integer page, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws ApplicationException {
        return ResponseEntity.ok(this.jobService.getAllPrivate(this.jwtService.extractUsernameFromAuthorizzation(authorization), jobType, page));
    }

    @GetMapping("/count/{jobType}")
    public ResponseEntity<GenericResponse<Long>> getCountAllPrivate(@PathVariable Integer jobType, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws ApplicationException {
        return ResponseEntity.ok(new GenericResponse<Long>(this.jobService.countAllPrivateByTypeAndUsername(this.jwtService.extractUsernameFromAuthorizzation(authorization), jobType)));
    }

    @GetMapping("/admin/{jobType}/{jobStatus}/{page}")
    public ResponseEntity<List<JobDTO>> getAllPrivateAdminPaginated(@PathVariable Integer jobType, @PathVariable Integer jobStatus, @PathVariable Integer page, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws ApplicationException {
        return ResponseEntity.ok(this.jobService.getAllPrivateByTypeAndStatus(jobType, jobStatus, this.jwtService.extractUsernameFromAuthorizzation(authorization), page));
    }

    @GetMapping("/admin/count/{jobType}/{jobStatus}")
    public ResponseEntity<GenericResponse<Long>> getCountAllAdminPrivate(@PathVariable Integer jobType, @PathVariable Integer jobStatus, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws ApplicationException {
        return ResponseEntity.ok(new GenericResponse<Long>(this.jobService.countAllPrivateByTypeAndStatus(jobType, jobStatus, this.jwtService.extractUsernameFromAuthorizzation(authorization))));
    }

    @GetMapping("/admin/{jobStatus}/{id}")
    public ResponseEntity<JobDTO> getPrivateByStatus(@PathVariable Long id, @PathVariable Integer jobStatus, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws ApplicationException {
        return ResponseEntity.ok(this.jobService.getPrivateByStatus(id, jobStatus, jwtService.extractUsernameFromAuthorizzation(authorization)));
    }

    @PostMapping
    public ResponseEntity<JobDTO> save(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @RequestBody JobDTO jobDTO) throws ApplicationException {
        return ResponseEntity.ok(this.jobService.save(jobDTO, this.jwtService.extractUsernameFromAuthorizzation(authorization)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobDTO> update(@PathVariable Long id, @RequestBody JobDTO jobDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws ApplicationException {
        return ResponseEntity.ok(this.jobService.update(id, jobDTO, this.jwtService.extractUsernameFromAuthorizzation(authorization)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws ApplicationException {
        this.jobService.delete(id, this.jwtService.extractUsernameFromAuthorizzation(authorization));
        return ResponseEntity.ok("OK");
    }
}
