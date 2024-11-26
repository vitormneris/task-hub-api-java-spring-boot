package com.codecrafters.taskhubcore.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Document(collection = "trabalhos")
public class JobEntity {
    @Id
    private String id;
    @Field(name = "titulo")
    private String title;
    @Field(name = "momento")
    private String moment;
    @Field(name = "detalhes")
    private String details;
    @Field(name = "imagem_url")
    private String imageUrl;
    @Field(name = "pagamento")
    private Double payment;
    @Field(name = "disponibilidade")
    private Boolean available;
    @Field(name = "endereco")
    private String address;
    @Field(name = "criador")
    private String crafterId;
    @Field(name = "inscritos")
    private Set<String> subscribersId = new HashSet<>();
}
