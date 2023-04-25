package com.andreidodu.controller;

import com.andreidodu.constants.JobConst;
import com.andreidodu.dto.JobDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.service.JobService;
import com.andreidodu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/job")
@RequiredArgsConstructor
public class JobController {

    final private JobService jobService;

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> get(@PathVariable Long id) throws ApplicationException {
        return ResponseEntity.ok(this.jobService.get(id));
    }

    @GetMapping("/offers/{page}")
    public ResponseEntity<List<JobDTO>> getAllOffers(@PathVariable Integer page) throws ApplicationException {
        return ResponseEntity.ok(this.jobService.getAll(JobConst.TYPE_OFFER, page));
    }

    @GetMapping("/requests/{page}")
    public ResponseEntity<List<JobDTO>> getAllRequests(@PathVariable Integer page) throws ApplicationException {
        return ResponseEntity.ok(this.jobService.getAll(JobConst.TYPE_REQUEST, page));
    }

    @PostMapping
    public ResponseEntity<JobDTO> save(@RequestBody JobDTO jobDTO) {
        jobDTO.setStatus(0);
        return ResponseEntity.ok(this.jobService.save(jobDTO));
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
