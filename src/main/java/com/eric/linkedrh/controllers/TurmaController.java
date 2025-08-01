package com.eric.linkedrh.controllers;

import com.eric.linkedrh.dtos.TurmaDto;
import com.eric.linkedrh.services.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/curso/{id}")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    @GetMapping("/turma")
    public List<TurmaDto> cadastroTurma(@PathVariable int id){
        return this.turmaService.getTurmas(id);
    }

    @PostMapping("/turma/cadastro")
    public String cadastrarTurma(@PathVariable int id, @RequestBody TurmaDto turmaDto){
        this.turmaService.createTurma(id,turmaDto);
        return "Turma Cadastrado com sucesso";
    }

    @PatchMapping("/turma/{id_turma}/atualiza")
    public String atualizaTurma(@PathVariable int id_turma,@RequestBody TurmaDto turmaDto) {
        this.turmaService.updateTurma(id_turma, turmaDto);
        return "Turma Atualizado com sucesso";
    }
    
    @DeleteMapping("/turma/{id_turma}/delete")
    public String deletarTurma(@PathVariable int id_turma) {
        this.turmaService.deleteTurma(id_turma);
        return "Turma Deletado com sucesso";
    }
}
