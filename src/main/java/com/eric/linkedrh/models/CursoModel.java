package com.eric.linkedrh.models;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CursoModel {
    private int codigo;
    private String nome;
    private String descricao;
    private int duracao;
}
