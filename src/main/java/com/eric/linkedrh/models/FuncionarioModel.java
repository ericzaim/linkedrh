package com.eric.linkedrh.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FuncionarioModel {
    private int id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String cargo;
    private LocalDate admissao;
    private int status;

}
