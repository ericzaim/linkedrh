package com.eric.linkedrh.service;

import com.eric.linkedrh.dao.TurmaDao;
import com.eric.linkedrh.dtos.FuncionarioDto;
import com.eric.linkedrh.dtos.TurmaDto;
import com.eric.linkedrh.dtos.TurmaPostDto;
import com.eric.linkedrh.models.FuncionarioModel;
import com.eric.linkedrh.services.TurmaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TurmaServiceTest {

    @InjectMocks
    private TurmaService turmaService;

    @Mock
    private TurmaDao turmaDao;

    @Test
    void testGetTurmas() {
        int idCurso = 1;
        List<TurmaDto> mockTurmas = List.of(new TurmaDto(LocalDate.now(),LocalDate.now().plusDays(1826),"Indaiatuba",1,10));
        when(turmaDao.findTurmaByCurso(idCurso)).thenReturn(mockTurmas);

        List<TurmaDto> resultado = turmaService.getTurmas(idCurso);

        assertEquals("Indaiatuba", resultado.getFirst().getLocal());
    }

    @Test
    void testGetFuncionariosByTurma() {
        int id = 1;
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 31);
        List<FuncionarioModel> funcionarios = List.of(
                new FuncionarioModel(1, "João", "12345678901", LocalDate.of(1990, 5, 10), "Desenvolvedor", LocalDate.of(2020, 1, 1), true)
        );

        when(turmaDao.findByTurma(id,inicio, fim)).thenReturn(funcionarios);

        List<FuncionarioDto> resultado = turmaService.getFuncionariosByTurma(id,inicio, fim);

        assertEquals("João", resultado.getFirst().getNome());
    }

    @Test
    void testIncluirAluno() {
        assertDoesNotThrow(() -> turmaService.incluirAluno(1, 1));
        verify(turmaDao).adicionarParticipantes(1, 1);
    }

    @Test
    void testExcluirAluno() {
        assertDoesNotThrow(() -> turmaService.excluirAluno(1, 1));
        verify(turmaDao).removerParticipantes(1, 1);
    }

    @Test
    void testCreateTurma() {
        TurmaPostDto turma = new TurmaPostDto(LocalDate.now(), LocalDate.now().plusDays(5), "Laboratório");
        assertDoesNotThrow(() -> turmaService.createTurma(2, turma));
        verify(turmaDao).createTurma(2, turma);
    }

    @Test
    void testUpdateTurma() {
        TurmaPostDto turma = new TurmaPostDto(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 15), "Sala B");
        assertDoesNotThrow(() -> turmaService.updateTurma(3, turma));
        verify(turmaDao).updateTurma(eq(3), anyMap());
    }

    @Test
    void testDeleteTurma() {
        assertDoesNotThrow(() -> turmaService.deleteTurma(10));
        verify(turmaDao).deleteTurma(10);
    }
}
