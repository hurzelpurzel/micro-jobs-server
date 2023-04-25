package com.andreidodu.controller;

import com.andreidodu.dto.PaymentTypeDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.service.JobService;
import com.andreidodu.service.PaymentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/paymentType")
@RequiredArgsConstructor
public class PaymentTypeController {

    final private PaymentTypeService paymentTypeService;

    @GetMapping("/{id}")
    public ResponseEntity<PaymentTypeDTO> get(@PathVariable Long id) throws ApplicationException {
        return ResponseEntity.ok(this.paymentTypeService.get(id));
    }

    @PostMapping
    public ResponseEntity<PaymentTypeDTO> save(@RequestBody PaymentTypeDTO userDTO) throws ApplicationException {
        return ResponseEntity.ok(this.paymentTypeService.save(userDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentTypeDTO> update(@PathVariable Long id, @RequestBody PaymentTypeDTO userDTO) throws ApplicationException {
        return ResponseEntity.ok(this.paymentTypeService.update(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        this.paymentTypeService.delete(id);
        return ResponseEntity.ok("OK");
    }
}
