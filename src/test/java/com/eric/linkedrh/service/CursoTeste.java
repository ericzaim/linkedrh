package com.eric.linkedrh.service;

import com.eric.linkedrh.dao.CursoDao;
import com.eric.linkedrh.dtos.CursoDto;
import com.eric.linkedrh.models.CursoModel;
import com.eric.linkedrh.services.CursoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CursoTeste {

    @Mock
    private CursoDao cursoDao;

    @InjectMocks
    private CursoService cursoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCursosSuccess() {
        CursoModel curso = new CursoModel(1,"Java", "Curso de Java", 40);
        when(cursoDao.findAll()).thenReturn(Arrays.asList(curso));

        List<CursoDto> cursos = cursoService.getCursos();

        assertEquals(1, cursos.size());
        assertEquals("Java", cursos.get(0).getNome());
    }

    @Test
    void testGetCursosEmpty() {
        when(cursoDao.findAll()).thenReturn(Collections.emptyList());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> cursoService.getCursos());
        assertTrue(ex.getMessage().contains("Ainda não há cursos cadastrados"));
    }

    @Test
    void testCreateCursoSuccess() {
        CursoDto dto = new CursoDto("Python", "Curso de Python", 30);
        String result = cursoService.createCurso(dto);
        assertEquals("Curso Python cadastrado", result);
        verify(cursoDao, times(1)).createCurso(dto);
    }

    @Test
    void testDeleteCurso() {
        cursoService.deleteCurso(1);
        verify(cursoDao).deleteCurso(1);
    }

    @Test
    void testUpdateCurso() {
        CursoDto dto = new CursoDto("Java", "Atualizado", 45);
        cursoService.updateCurso(dto, 1);
        verify(cursoDao).updateCurso(eq(1), anyMap());
    }
}