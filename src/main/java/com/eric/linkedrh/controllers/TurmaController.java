package com.eric.linkedrh.controllers;

import com.eric.linkedrh.dtos.FuncionarioDto;
import com.eric.linkedrh.dtos.TurmaDto;
import com.eric.linkedrh.dtos.TurmaPostDto;
import com.eric.linkedrh.services.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller responsável pelo CRUD das turmas de um curso.
 * Permite consultar turmas de um curso, gerenciar turmas específicas e seus participantes.
 */
@RestController
@RequestMapping("/curso")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    /**
     * Retorna a lista de turmas associadas a um curso específico.
     *
     * @param id Identificador do curso.
     * @return Lista de TurmaDto representando as turmas do curso.
     */
    @GetMapping("/{id}/turma")
    public List<TurmaDto> getTurma(@PathVariable int id){
        return this.turmaService.getTurmas(id);
    }

    /**
     * Retorna a lista de funcionários (participantes) associados a uma turma específica.
     *
     * @param inicio Data de inicio da turma.
     * @param fim  Data d fim da turma
     * @return Lista de FuncionarioDto representando os participantes da turma.
     */
    @GetMapping("{id}/turma/{inicio}/{fim}/funcionario")
    public List<FuncionarioDto>getFuncionarioByTurma(@PathVariable("id") int id,@PathVariable("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,@PathVariable("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim){
        return this.turmaService.getFuncionariosByTurma(id,inicio,fim);
    }
    /**
     * Cria uma nova turma associada a um curso.
     *
     * @param id Identificador do curso ao qual a turma será vinculada.
     * @param turmaDto Dados da turma a ser criada.
     * @return Mensagem de sucesso após o cadastro.
     */
    @PostMapping("/{id}/turma/cadastro")
    public String cadastrarTurma(@PathVariable int id, @RequestBody TurmaPostDto turmaDto){
        this.turmaService.createTurma(id,turmaDto);
        return "Turma Cadastrado com sucesso";
    }

    /**
     * Atualiza os dados de uma turma existente.
     *
     * @param id_turma Identificador da turma a ser atualizada.
     * @param turmaPostDto Dados atualizados da turma.
     * @return Mensagem de sucesso após a atualização.
     */
    @PatchMapping("/turma/{id_turma}/atualiza")
    public String atualizaTurma(@PathVariable int id_turma,@RequestBody TurmaPostDto turmaPostDto) {
        this.turmaService.updateTurma(id_turma, turmaPostDto);
        return "Turma Atualizado com sucesso";
    }

    /**
     * Remove uma turma existente.
     *
     * @param id_turma Identificador da turma a ser removida.
     * @return Mensagem de sucesso após a remoção.
     */
    @DeleteMapping("/turma/{id_turma}/delete")
    public String deletarTurma(@PathVariable int id_turma) {
        this.turmaService.deleteTurma(id_turma);
        return "Turma Deletado com sucesso";
    }

    /**
     * Inclui um participante (aluno) em uma turma.
     *
     * @param id_funcionario Identificador do participante a ser incluído.
     * @param id_turma Identificador da turma onde o participante será incluído.
     * @return Mensagem de sucesso após inclusão.
     */
    @PostMapping("/turma/{id_turma}/participante/{id_funcionario}/incluir")
    public String incluirParticipante( @PathVariable("id_turma") int id_turma,@PathVariable("id_funcionario") int id_funcionario) {
        this.turmaService.incluirAluno(id_turma,id_funcionario);
        return String.format("Participante %d Cadastrado na turma %d com sucesso",id_funcionario,id_turma);
    }

    /**
     * Remove um participante (aluno) de uma turma.
     *
     * @param id_turma Identificador da turma da qual o participante será removido.
     * @param id_aluno Identificador do participante a ser removido.
     * @return Mensagem de sucesso após remoção.
     */
    @DeleteMapping("/turma/{id_turma}/participante/{id_aluno}/delete")
    public String removerParticipante(@PathVariable int id_turma, @PathVariable int id_aluno){
        this.turmaService.excluirAluno(id_aluno,id_turma);
        return String.format("Aluno %d removido com sucesso da turma %d",id_aluno,id_turma);
    }
}
