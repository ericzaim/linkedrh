package com.eric.linkedrh.controllers;


import com.eric.linkedrh.dtos.CursoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.eric.linkedrh.services.CursoService;

@RestController
@RequestMapping("/api")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping("/curso/{id}")
    public CursoDto getCurso(@PathVariable Integer id){
        return this.cursoService.getCursoById(id);
    }
}
