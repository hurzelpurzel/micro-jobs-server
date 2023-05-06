package com.andreidodu.controller;

import com.andreidodu.dto.ConversationDTO;
import com.andreidodu.dto.MessageDTO;
import com.andreidodu.dto.MessageRequestDTO;
import com.andreidodu.dto.MessageResponseDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.service.JwtService;
import com.andreidodu.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/message")
@RequiredArgsConstructor
public class MessageController {

    final private MessageService messageService;
    final private JwtService jwtService;


    @GetMapping
    public ResponseEntity<List<ConversationDTO>> getConversations(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws ApplicationException {
        return ResponseEntity.ok(this.messageService.getConversations(this.jwtService.extractUsernameFromAuthorizzation(authorization)));
    }

    @GetMapping("/{userToId}/{jobId}")
    public ResponseEntity<List<MessageResponseDTO>> getConversationMessages(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @PathVariable Long userToId, @PathVariable Long jobId) throws ApplicationException {
        return ResponseEntity.ok(this.messageService.getConversationMessages(this.jwtService.extractUsernameFromAuthorizzation(authorization), userToId, jobId));
    }

    @PostMapping
    public ResponseEntity<MessageResponseDTO> save(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @RequestBody MessageRequestDTO messageRequestDTO) throws ApplicationException {
        return ResponseEntity.ok(this.messageService.save(this.jwtService.extractUsernameFromAuthorizzation(authorization), messageRequestDTO));
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
