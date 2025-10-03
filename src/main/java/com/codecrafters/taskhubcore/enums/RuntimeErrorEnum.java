package com.codecrafters.taskhubcore.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RuntimeErrorEnum {
    ERR0001("RESOURCE_NOT_FOUND", "Este usuário não foi encontrado"),
    ERR0002("RESOURCE_NOT_FOUND", "Este trabalho não foi encontrado"),
    ERR0003("NOT_AUTHORIZED", "E-mail ou senha não encontrados"),
    ERR0004("DATA_INTEGRATION_VIOLATED", "Este E-mail já existe na base de dados"),
    ERR0005("DATA_INTEGRATION_VIOLATED", "Este usuário já está inscrito neste trabalho"),
    ERR0006("DATA_INTEGRATION_VIOLATED", "Este usuário não está inscrito neste trabalho");

    private final String code;
    private final String message;
}
