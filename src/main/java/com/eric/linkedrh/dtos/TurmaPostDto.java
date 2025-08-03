package com.eric.linkedrh.dtos;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TurmaPostDto {
    private LocalDate inicio;
    private LocalDate fim;
    private String local;
}
