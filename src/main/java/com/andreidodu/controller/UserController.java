package com.andreidodu.controller;

import com.andreidodu.dto.UserDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    final private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> get(@PathVariable Long id) throws ApplicationException {
        return ResponseEntity.ok(this.userService.get(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO userDTO) {
        userDTO.setStatus(0);
        return ResponseEntity.ok(this.userService.save(userDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO userDTO) throws ApplicationException {
        return ResponseEntity.ok(this.userService.update(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        this.userService.delete(id);
        return ResponseEntity.ok("OK");
    }
}
