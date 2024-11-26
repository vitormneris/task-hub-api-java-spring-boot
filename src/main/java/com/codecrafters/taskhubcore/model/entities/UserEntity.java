package com.codecrafters.taskhubcore.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
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
    private Set<String> jobsIdCreated  = new HashSet<>();
    @Field(name = "trabalhos_inscrito")
    private Set<String> jobsIdSubscribed  = new HashSet<>();
}
