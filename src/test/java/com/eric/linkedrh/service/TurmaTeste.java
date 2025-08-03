package com.eric.linkedrh.service;

import com.eric.linkedrh.dao.TurmaDao;
import com.eric.linkedrh.dtos.FuncionarioDto;
import com.eric.linkedrh.dtos.TurmaDto;
import com.eric.linkedrh.models.FuncionarioModel;
import com.eric.linkedrh.services.TurmaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TurmaServiceTest {

    @InjectMocks
    private TurmaService turmaService;

    @Mock
    private TurmaDao turmaDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIncluirAluno() {
        doNothing().when(turmaDao).adicionarParticipantes(10, 2);
        turmaService.incluirAluno(10, 2);
        verify(turmaDao).adicionarParticipantes(10, 2);
    }

    @Test
    void testExcluirAluno() {
        doNothing().when(turmaDao).removerParticipantes(10, 2);
        turmaService.excluirAluno(10, 2);
        verify(turmaDao).removerParticipantes(10, 2);
    }

    @Test
    void testCreateTurma() {
        TurmaDto turma = new TurmaDto();
        doNothing().when(turmaDao).createTurma(1, turma);

        turmaService.createTurma(1, turma);

        verify(turmaDao).createTurma(1, turma);
    }

    @Test
    void testUpdateTurma() {
        TurmaDto turma = new TurmaDto();
        turma.setInicio(LocalDate.of(2025, 1, 1));
        turma.setFim(LocalDate.of(2025, 2, 1));
        turma.setLocal("Sala 1");

        turmaService.updateTurma(5, turma);

        verify(turmaDao).updateTurma(eq(5), argThat(map ->
                map.get("inicio").equals(turma.getInicio()) &&
                        map.get("fim").equals(turma.getFim()) &&
                        map.get("local").equals("Sala 1")
        ));
    }

    @Test
    void testDeleteTurma() {
        turmaService.deleteTurma(3);
        verify(turmaDao).deleteTurma(3);
    }

}
