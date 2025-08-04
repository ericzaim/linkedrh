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
public class TurmaModel {
    private int codigo;
    private LocalDate inicio;
    private LocalDate fim;
    private String local;
    private int Curso;

}
