package com.eric.linkedrh.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TurmaParticipanteModel {
    private int codigo;
    private int turma;
    private int funcionario;
}
