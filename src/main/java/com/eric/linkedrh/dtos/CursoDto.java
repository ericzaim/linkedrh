package com.eric.linkedrh.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CursoDto {
    private String nome;
    private String descricao;
    private int duracao;
}
