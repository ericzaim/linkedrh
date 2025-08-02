package com.eric.linkedrh.controllers;

import com.eric.linkedrh.dtos.FuncionarioDto;
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
    @GetMapping("/turma/{id_turma}/funcionario")
    public List<FuncionarioDto> getFuncionarioByTurma(@PathVariable int id_turma){
        return this.turmaService.getFuncionariosByTurma(id_turma);
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

    @PostMapping("/turma/{id_turma}/cadastro/participante")
    public String incluirParticipante(@PathVariable int id, @PathVariable int id_turma){
        this.turmaService.incluirAluno(id,id_turma);
        return String.format("Participante %d Cadastrado na turma %d com sucesso",id,id_turma);
    }
    @DeleteMapping("/turma/{id_turma}/delete/participante/{id_aluno}")
    public String deletarParticipante(@PathVariable int id_turma, @PathVariable int id_aluno){
        this.turmaService.excluirAluno(id_aluno,id_turma);
        return String.format("Aluno %d removido com sucesso da turma %d",id_aluno,id_turma);
    }
}
