package com.eric.linkedrh.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TurmaDto {
    private LocalDate inicio;
    private LocalDate fim;
    private String local;
    private int curso;
}
