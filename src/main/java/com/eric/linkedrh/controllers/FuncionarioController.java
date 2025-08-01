package com.eric.linkedrh.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.eric.linkedrh.dtos.FuncionarioDto;
import com.eric.linkedrh.services.FuncionarioService;

@RestController
public class FuncionarioController {
    
    @Autowired
    private FuncionarioService funcionarioService;


    @GetMapping("/curso")
    public List<FuncionarioDto> getAllFuncionarios(){
        return this.funcionarioService.getAllFuncionarios();
    }
    
    @GetMapping("/curso/{id_curso}/turma/{id_turma}/funcionario")
    public List<FuncionarioDto> getFuncionarioByTurma(@PathVariable int id_turma){
        return this.funcionarioService.getFuncionariosByTurma(id_turma);
    }
}
