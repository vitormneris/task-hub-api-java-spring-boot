package com.codecrafters.taskhubcore.controller;

import com.codecrafters.taskhubcore.model.entities.UserEntity;
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
    public ResponseEntity<UserEntity> findById(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @GetMapping("/{email}/encontrar-email")
    public ResponseEntity<UserEntity> findByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok().body(userService.findByEmail(email));
    }

    @PatchMapping("/{userId}/{jobId}/inscrever")
    public ResponseEntity<UserEntity> subscribeJob(@PathVariable("userId") String userId, @PathVariable("jobId") String jobId) {
        return ResponseEntity.ok().body(userService.subscribeJob(userId, jobId));
    }

    @PatchMapping("/{userId}/{jobId}/desinscrever")
    public ResponseEntity<UserEntity> unsubscribeJob(@PathVariable("userId") String userId, @PathVariable("jobId") String jobId) {
        return ResponseEntity.ok().body(userService.unsubscribeJob(userId, jobId));
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody UserEntity user) {
        return ResponseEntity.ok().body(userService.login(user));
    }

    @PostMapping("/inserir")
    public ResponseEntity<UserEntity> insert(@RequestBody UserEntity user) {
        UserEntity userEntity = userService.save(user);
        if (userEntity == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userEntity);
    }

    @PutMapping("/{id}/atualizar")
    public ResponseEntity<UserEntity> update(@PathVariable("id") String id, @RequestBody UserEntity user) {
        return ResponseEntity.ok().body(userService.update(user, id));
    }

    @DeleteMapping("/{id}/deletar")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
