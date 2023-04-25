package com.andreidodu.controller;

import com.andreidodu.dto.JobPictureDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.service.JobPictureService;
import com.andreidodu.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/jobPicture")
@RequiredArgsConstructor
public class JobPictureController {

    final private JobPictureService jobPictureService;

    @GetMapping("/{id}")
    public ResponseEntity<JobPictureDTO> get(@PathVariable Long id) throws ApplicationException {
        return ResponseEntity.ok(this.jobPictureService.get(id));
    }

    @PostMapping
    public ResponseEntity<JobPictureDTO> save(@RequestBody JobPictureDTO jobPictureDTO) throws ApplicationException {
        return ResponseEntity.ok(this.jobPictureService.save(jobPictureDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobPictureDTO> update(@PathVariable Long id, @RequestBody JobPictureDTO jobPictureDTO) throws ApplicationException {
        return ResponseEntity.ok(this.jobPictureService.update(id, jobPictureDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        this.jobPictureService.delete(id);
        return ResponseEntity.ok("OK");
    }
}
