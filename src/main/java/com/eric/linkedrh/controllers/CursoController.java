package com.eric.linkedrh.controllers;


import com.eric.linkedrh.dtos.CursoDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.eric.linkedrh.services.CursoService;
import java.util.List;

@RestController
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping()
    public List<CursoDto> getCurso(){
        return this.cursoService.getCursos();
    }

    @PostMapping("/cadastro")
    public String postCurso(@RequestBody CursoDto curso){
        this.cursoService.createCurso(curso);
        return String.format("Curso %s cadastrado com sucesso",curso.getNome());
    }
    @PatchMapping("/atualiza/{id}")
    public String updateCurso(@RequestBody CursoDto curso, @PathVariable int id){
        this.cursoService.updateCurso(curso,id);
        return String.format("Curso %d atualizado com sucesso",id);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteCurso(@PathVariable int id){
        this.cursoService.deleteCurso(id);
        return String.format("Curso %d deletado com sucesso",id);
    }
}
