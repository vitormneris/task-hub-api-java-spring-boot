package com.codecrafters.taskhubcore.controller;

import com.codecrafters.taskhubcore.model.entities.JobEntity;
import com.codecrafters.taskhubcore.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/trabalhos")
public class JobController {
    private final JobService jobService;

    @GetMapping("/todos")
    public ResponseEntity<List<JobEntity>> findAll() {
        return ResponseEntity.ok().body(jobService.findAll());
    }

    @GetMapping("/{id}/encontrar-id")
    public ResponseEntity<JobEntity> findById(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(jobService.findById(id));
    }

    @PostMapping("/inserir")
    public ResponseEntity<JobEntity> insert(@RequestBody JobEntity job) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.save(job));
    }

    @PatchMapping("/{id}/atualizar-disponibilidade")
    public ResponseEntity<JobEntity> updateAvailable(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(jobService.updateAvailable(id));
    }

    @PutMapping("/{id}/atualizar")
    public ResponseEntity<JobEntity> update(@PathVariable("id") String id, @RequestBody JobEntity job) {
        return ResponseEntity.ok().body(jobService.update(job, id));
    }

    @DeleteMapping("/{id}/deletar")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
       jobService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
