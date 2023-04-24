package com.andreidodu.controller;

import com.andreidodu.dto.JobDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.service.JobService;
import com.andreidodu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/job")
@RequiredArgsConstructor
public class JobController {

    final private JobService jobService;

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> get(@PathVariable Long id) throws ApplicationException {
        return ResponseEntity.ok(this.jobService.get(id));
    }

    @PostMapping
    public ResponseEntity<JobDTO> save(@RequestBody JobDTO userDTO) {
        userDTO.setStatus(0);
        return ResponseEntity.ok(this.jobService.save(userDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobDTO> update(@PathVariable Long id, @RequestBody JobDTO userDTO) throws ApplicationException {
        return ResponseEntity.ok(this.jobService.update(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        this.jobService.delete(id);
        return ResponseEntity.ok("OK");
    }
}
