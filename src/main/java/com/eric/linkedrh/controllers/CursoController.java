package com.eric.linkedrh.controllers;

import com.eric.linkedrh.dtos.CursoDto;
import com.eric.linkedrh.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller do Curso.
 *
 * endpoints para CRUD de cursos.
 */
@RestController
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    /**
     * Endpoint para obter a lista de todos os cursos cadastrados.
     *
     * @return Lista de objetos CursoDto representando os cursos.
     */
    @GetMapping()
    public List<CursoDto> getCurso(){
        return this.cursoService.getCursos();
    }

    /**
     * Endpoint para cadastrar um novo curso.
     *
     * @param curso Objeto CursoDto contendo os dados do curso a ser criado.
     * @return Mensagem confirmando o cadastro do curso.
     */
    @PostMapping("/cadastro")
    public String postCurso(@RequestBody CursoDto curso){
        this.cursoService.createCurso(curso);
        return String.format("Curso %s cadastrado com sucesso",curso.getNome());
    }

    /**
     * Endpoint para atualizar os dados de um curso existente.
     *
     * @param curso Objeto CursoDto contendo os novos dados do curso.
     * @param id Identificador do curso a ser atualizado.
     * @return Mensagem confirmando a atualização do curso.
     */
    @PatchMapping("/atualiza/{id}")
    public String updateCurso(@RequestBody CursoDto curso, @PathVariable int id){
        this.cursoService.updateCurso(curso,id);
        return String.format("Curso %d atualizado com sucesso",id);
    }

    /**
     * Endpoint para deletar um curso pelo seu identificador.
     *
     * @param id Identificador do curso a ser deletado.
     * @return Mensagem confirmando a deleção do curso.
     */
    @DeleteMapping("/delete/{id}")
    public String deleteCurso(@PathVariable int id){
        this.cursoService.deleteCurso(id);
        return String.format("Curso %d deletado com sucesso",id);
    }
}
