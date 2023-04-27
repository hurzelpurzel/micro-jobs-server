package com.andreidodu.controller;

import com.andreidodu.constants.JobConst;
import com.andreidodu.dto.GenericResponse;
import com.andreidodu.dto.JobDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.service.JobService;
import com.andreidodu.service.UserService;
import com.andreidodu.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/job")
@RequiredArgsConstructor
public class JobController {

    final private JobService jobService;

    final private JwtService jwtService;

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> get(@PathVariable Long id) throws ApplicationException {
        return ResponseEntity.ok(this.jobService.get(id));
    }

    @GetMapping("/offers/{page}")
    public ResponseEntity<List<JobDTO>> getAllOffers(@PathVariable Integer page) throws ApplicationException {
        return ResponseEntity.ok(this.jobService.getAll(JobConst.TYPE_OFFER, page));
    }

    @GetMapping("/count/requests")
    public ResponseEntity<GenericResponse<Long>> getCountRequests() throws ApplicationException {
        return ResponseEntity.ok(new GenericResponse<Long>(this.jobService.countByType(JobConst.TYPE_REQUEST)));
    }

    @GetMapping("/count/offers")
    public ResponseEntity<GenericResponse<Long>> getCountOffers() throws ApplicationException {
        return ResponseEntity.ok(new GenericResponse<Long>(this.jobService.countByType(JobConst.TYPE_OFFER)));
    }

    @GetMapping("/requests/{page}")
    public ResponseEntity<List<JobDTO>> getAllRequests(@PathVariable Integer page) throws ApplicationException {
        return ResponseEntity.ok(this.jobService.getAll(JobConst.TYPE_REQUEST, page));
    }

    @PostMapping
    public ResponseEntity<JobDTO> save(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @RequestBody JobDTO jobDTO) throws ApplicationException {
        return ResponseEntity.ok(this.jobService.save(jobDTO, this.jwtService.extractUsername(authorization.substring(7))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobDTO> update(@PathVariable Long id, @RequestBody JobDTO jobDTO) throws ApplicationException {
        return ResponseEntity.ok(this.jobService.update(id, jobDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        this.jobService.delete(id);
        return ResponseEntity.ok("OK");
    }
}
