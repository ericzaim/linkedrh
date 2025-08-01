package com.eric.linkedrh.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TurmaParticipanteDto{
    private int codigo;
    private int turma;
    private int funcionario;
}