package com.codecrafters.taskhubcore.controller.user;

import com.codecrafters.taskhubcore.controller.user.dto.UserDTO;
import com.codecrafters.taskhubcore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/usuarios")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}/encontrar-id")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @GetMapping("/{email}/encontrar-email")
    public ResponseEntity<UserDTO> findByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok().body(userService.findByEmail(email));
    }

    @PatchMapping("/{userId}/{jobId}/inscrever")
    public ResponseEntity<UserDTO> subscribeJob(@PathVariable("userId") String userId, @PathVariable("jobId") String jobId) {
        return ResponseEntity.ok().body(userService.subscribeJob(userId, jobId));
    }

    @PatchMapping("/{userId}/{jobId}/desinscrever")
    public ResponseEntity<UserDTO> unsubscribeJob(@PathVariable("userId") String userId, @PathVariable("jobId") String jobId) {
        return ResponseEntity.ok().body(userService.unsubscribeJob(userId, jobId));
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody UserDTO user) {
        return ResponseEntity.ok().body(userService.login(user));
    }

    @PostMapping("/inserir")
    public ResponseEntity<UserDTO> insert(@RequestBody UserDTO user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/{id}/atualizar")
    public ResponseEntity<UserDTO> update(@PathVariable("id") String id, @RequestBody UserDTO user) {
        return ResponseEntity.ok().body(userService.update(user, id));
    }

    @DeleteMapping("/{id}/deletar")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
