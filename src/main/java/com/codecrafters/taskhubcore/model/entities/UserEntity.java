package com.codecrafters.taskhubcore.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_usuarios")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "nome")
    private String name;
    private String email;
    @Column(name = "telefone")
    private String phone;
    @Column(name = "senha")
    private String password;
    @Column(name = "imagem")
    private String imageUrl;

    @OneToMany(mappedBy = "crafter")
    private Set<JobEntity> jobsCreated  = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tb_inscrito_trabalho", joinColumns = @JoinColumn(name = "inscrito_id"), inverseJoinColumns = @JoinColumn(name = "trabalho_id"))
    private Set<JobEntity> jobsSubscribed  = new HashSet<>();
}
