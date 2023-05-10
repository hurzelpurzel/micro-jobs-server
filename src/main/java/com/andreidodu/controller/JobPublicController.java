package com.andreidodu.controller;

import com.andreidodu.dto.JobDTO;
import com.andreidodu.dto.JobListPageDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/job/public")
@RequiredArgsConstructor
public class JobPublicController {

    final private JobService jobService;

    @GetMapping("/jobId/{id}")
    public ResponseEntity<JobDTO> get(@PathVariable Long id) throws ApplicationException {
        return ResponseEntity.ok(this.jobService.getPublic(id));
    }

    @GetMapping("/jobType/{jobType}/page/{page}")
    public ResponseEntity<JobListPageDTO> getJobsByTypePaginated(@PathVariable Integer jobType, @PathVariable Integer page) throws ApplicationException {
        JobListPageDTO result = new JobListPageDTO(this.jobService.getAllPublic(jobType, page), this.jobService.countAllPublicByType(jobType));
        return ResponseEntity.ok(result);
    }

}
