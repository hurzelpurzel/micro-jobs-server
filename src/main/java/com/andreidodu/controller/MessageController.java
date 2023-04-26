package com.andreidodu.controller;

import com.andreidodu.dto.MessageDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.service.JobService;
import com.andreidodu.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/message")
@RequiredArgsConstructor
public class MessageController {

    final private MessageService messageService;

    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> get(@PathVariable Long id) throws ApplicationException {
        return ResponseEntity.ok(this.messageService.get(id));
    }

    @PostMapping
    public ResponseEntity<MessageDTO> save(@RequestBody MessageDTO messageDTO) throws ApplicationException {
        return ResponseEntity.ok(this.messageService.save(messageDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageDTO> update(@PathVariable Long id, @RequestBody MessageDTO messageDTO) throws ApplicationException {
        return ResponseEntity.ok(this.messageService.update(id, messageDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        this.messageService.delete(id);
        return ResponseEntity.ok("OK");
    }
}
