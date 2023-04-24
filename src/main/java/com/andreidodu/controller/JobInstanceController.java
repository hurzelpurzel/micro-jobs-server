package com.andreidodu.controller;

import com.andreidodu.dto.JobInstanceDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.service.JobInstanceService;
import com.andreidodu.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/jobInstance")
@RequiredArgsConstructor
public class JobInstanceController {

    final private JobInstanceService jobInstanceService;

    @GetMapping("/{id}")
    public ResponseEntity<JobInstanceDTO> get(@PathVariable Long id) throws ApplicationException {
        return ResponseEntity.ok(this.jobInstanceService.get(id));
    }

    @PostMapping
    public ResponseEntity<JobInstanceDTO> save(@RequestBody JobInstanceDTO userDTO) throws ApplicationException {
        userDTO.setStatus(0);
        return ResponseEntity.ok(this.jobInstanceService.save(userDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobInstanceDTO> update(@PathVariable Long id, @RequestBody JobInstanceDTO userDTO) throws ApplicationException {
        return ResponseEntity.ok(this.jobInstanceService.update(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        this.jobInstanceService.delete(id);
        return ResponseEntity.ok("OK");
    }
}
