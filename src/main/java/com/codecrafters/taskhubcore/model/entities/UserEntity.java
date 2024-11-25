package com.codecrafters.taskhubcore.model.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "usuarios")
public class UserEntity {
    @Id
    private String id;
    @Field(name = "nome")
    private String name;
    private String email;
    @Field(name = "telefone")
    private String phone;
    @Field(name = "senha")
    private String password;
    @Field(name = "trabalhos_criado")
    private Set<JobEntity> jobsCreated;
    @Field(name = "trabalhos_inscrito")
    private Set<JobEntity> jobsSubscribed;
}
