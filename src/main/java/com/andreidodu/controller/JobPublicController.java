package com.andreidodu.controller;

import com.andreidodu.dto.GenericResponse;
import com.andreidodu.dto.JobDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/job/public")
@RequiredArgsConstructor
public class JobPublicController {

    final private JobService jobService;

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> get(@PathVariable Long id) throws ApplicationException {
        return ResponseEntity.ok(this.jobService.getPublic(id));
    }

    @GetMapping("/{jobType}/{page}")
    public ResponseEntity<List<JobDTO>> getJobsByTypePaginated(@PathVariable Integer jobType, @PathVariable Integer page) throws ApplicationException {
        return ResponseEntity.ok(this.jobService.getAllPublic(jobType, page));
    }

    @GetMapping("/count/{jobType}")
    public ResponseEntity<GenericResponse<Long>> countAllJobsByJobType(@PathVariable Integer jobType) throws ApplicationException {
        return ResponseEntity.ok(new GenericResponse<Long>(this.jobService.countAllPublicByType(jobType)));
    }

}
