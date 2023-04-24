package com.andreidodu.controller;

import com.andreidodu.dto.RatingDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.service.JobInstanceService;
import com.andreidodu.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rating")
@RequiredArgsConstructor
public class RatingController {

    final private RatingService ratingService;

    @GetMapping("/{id}")
    public ResponseEntity<RatingDTO> get(@PathVariable Long id) throws ApplicationException {
        return ResponseEntity.ok(this.ratingService.get(id));
    }

    @PostMapping
    public ResponseEntity<RatingDTO> save(@RequestBody RatingDTO ratingDTO) throws ApplicationException {
        return ResponseEntity.ok(this.ratingService.save(ratingDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingDTO> update(@PathVariable Long id, @RequestBody RatingDTO ratingDTO) throws ApplicationException {
        return ResponseEntity.ok(this.ratingService.update(id, ratingDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        this.ratingService.delete(id);
        return ResponseEntity.ok("OK");
    }
}
