package com.eric.linkedrh.dtos;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TurmaPostDto {
    private LocalDate inicio;
    private LocalDate fim;
    private String local;
}
