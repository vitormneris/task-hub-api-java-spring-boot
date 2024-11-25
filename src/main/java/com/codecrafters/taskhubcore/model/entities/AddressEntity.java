package com.codecrafters.taskhubcore.model.entities;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {
    @Field(name = "estado")
    private String state;
    @Field(name = "cidade")
    private String city;
    @Field(name = "bairro")
    private String neighborhood;
    @Field(name = "rua")
    private String street;
    @Field(name = "CEP")
    private String postalCode;
    @Field(name = "numero_residencia")
    private Integer number;
    @Field(name = "complemento")
    private String complement;
}
