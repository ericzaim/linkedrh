package com.eric.linkedrh.service;

import com.eric.linkedrh.dao.CursoDao;
import com.eric.linkedrh.dtos.CursoDto;
import com.eric.linkedrh.models.CursoModel;
import com.eric.linkedrh.services.CursoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CursoServiceTeste {

    @InjectMocks
    private CursoService cursoService;

    @Mock
    private CursoDao cursoDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCursos() {
        List<CursoModel> cursos = List.of(
                new CursoModel(1, "Curso Python", "DSA with Python", 120),
                new CursoModel(2, "Curso Java Spring", "Java Spring Framework para iniciantes", 240)
        );

        when(cursoDao.findAll()).thenReturn(cursos);

        List<CursoDto> result = cursoService.getCursos();

        assertEquals(2, result.size());
        assertEquals("Curso Python", result.getFirst().getNome());
    }

    @Test
    public void testGetCursosSemCursos() {
        when(cursoDao.findAll()).thenReturn(Collections.emptyList());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> cursoService.getCursos());
        assertTrue(thrown.getMessage().contains("Ainda não há cursos cadastrados"));
    }

    @Test
    public void testCreateCurso() {
        CursoDto dto = new CursoDto("Teste", "Descrição", 30);
        doNothing().when(cursoDao).createCurso(dto);

        String result = cursoService.createCurso(dto);

        assertEquals("Curso Teste cadastrado", result);
    }

    @Test
    public void testCreateUsuarioExcecao() {
        CursoDto dto = new CursoDto("Teste", "Descrição", 30);
        doThrow(new RuntimeException("erro")).when(cursoDao).createCurso(dto);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> cursoService.createCurso(dto));
        assertEquals("Erro ao criar usuário", thrown.getMessage());
    }

    @Test
    public void testUpdateCurso() {
        CursoDto dto = new CursoDto("Novo Nome", "Nova Desc", 90);
        doNothing().when(cursoDao).updateCurso(eq(1), anyMap());

        assertDoesNotThrow(() -> cursoService.updateCurso(dto, 1));
    }

    @Test
    public void testUpdateCursoExcecao() {
        CursoDto dto = new CursoDto("Nome", null, 0);
        doThrow(new RuntimeException("Erro SQL")).when(cursoDao).updateCurso(eq(1), anyMap());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> cursoService.updateCurso(dto, 1));
        assertTrue(thrown.getMessage().contains("Erro ao atualizar curso"));
    }

    @Test
    public void testDeleteCurso() {
        doNothing().when(cursoDao).deleteCurso(1);

        assertDoesNotThrow(() -> cursoService.deleteCurso(1));
    }
}
