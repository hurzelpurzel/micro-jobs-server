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
@RequestMapping(value = "/api/v1/job/public")
@RequiredArgsConstructor
public class JobPublicController {

    final private JobService jobService;

    final private JwtService jwtService;

    @GetMapping("/{jobType}/{page}")
    public ResponseEntity<List<JobDTO>> getJobsByTypePaginated(@PathVariable Integer jobType, @PathVariable Integer page) throws ApplicationException {
        return ResponseEntity.ok(this.jobService.getAll(jobType, page));
    }

    @GetMapping("/count/{jobType}")
    public ResponseEntity<GenericResponse<Long>> countAllJobsByJobType(@PathVariable Integer jobType) throws ApplicationException {
        return ResponseEntity.ok(new GenericResponse<Long>(this.jobService.countByType(jobType)));
    }

}
