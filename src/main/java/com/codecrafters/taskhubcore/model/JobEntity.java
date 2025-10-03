package com.codecrafters.taskhubcore.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_trabalhos")
public class JobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "titulo")
    private String title;
    @Column(name = "momento")
    private String moment;
    @Column(name = "detalhes")
    private String details;
    @Column(name = "imagem_url")
    private String imageUrl;
    @Column(name = "pagamento")
    private Double payment;
    @Column(name = "disponibilidade")
    private Boolean available;
    @Column(name = "endereco")
    private String address;

    @ManyToOne
    @JoinColumn(name = "criador_id")
    private UserEntity crafter;

    @ManyToMany(mappedBy = "jobsSubscribed")
    private Set<UserEntity> subscribers = new HashSet<>();
}
