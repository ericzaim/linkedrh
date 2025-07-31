package com.eric.linkedrh.controllers;


import com.eric.linkedrh.dtos.CursoDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.eric.linkedrh.services.CursoService;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping("/curso")
    public List<CursoDto> getCurso(){
        return this.cursoService.getCursos();
    }
    @PostMapping("/curso/cadastro")
    public String postCurso(@RequestBody CursoDto curso){
        this.cursoService.createCurso(curso);
        return String.format("Curso %s cadastrado com sucesso",curso.getNome());
    }
}
