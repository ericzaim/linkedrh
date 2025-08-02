package com.eric.linkedrh.controllers;

import com.eric.linkedrh.dtos.FuncionarioDto;
import com.eric.linkedrh.dtos.TurmaDto;
import com.eric.linkedrh.services.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelo CRUD das turmas de um curso.
 *
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
     * @param id_turma Identificador da turma.
     * @return Lista de FuncionarioDto representando os participantes da turma.
     */
    @GetMapping("/turma/{id_turma}/funcionario")
    public List<FuncionarioDto> getFuncionarioByTurma(@PathVariable int id_turma){
        return this.turmaService.getFuncionariosByTurma(id_turma);
    }

    /**
     * Cria uma nova turma associada a um curso.
     *
     * @param id Identificador do curso ao qual a turma será vinculada.
     * @param turmaDto Dados da turma a ser criada.
     * @return Mensagem de sucesso após o cadastro.
     */
    @PostMapping("/{id}/turma/cadastro")
    public String cadastrarTurma(@PathVariable int id, @RequestBody TurmaDto turmaDto){
        this.turmaService.createTurma(id,turmaDto);
        return "Turma Cadastrado com sucesso";
    }

    /**
     * Atualiza os dados de uma turma existente.
     *
     * @param id_turma Identificador da turma a ser atualizada.
     * @param turmaDto Dados atualizados da turma.
     * @return Mensagem de sucesso após a atualização.
     */
    @PatchMapping("/turma/{id_turma}/atualiza")
    public String atualizaTurma(@PathVariable int id_turma,@RequestBody TurmaDto turmaDto) {
        this.turmaService.updateTurma(id_turma, turmaDto);
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
    @PostMapping("/turma/{id_turma}/cadastro/participante/{id_funcionario}")
    public String incluirParticipante( @PathVariable int id_turma,@PathVariable int id_funcionario) {
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
    @DeleteMapping("/turma/{id_turma}/delete/participante/{id_aluno}")
    public String removerParticipante(@PathVariable int id_turma, @PathVariable int id_aluno){
        this.turmaService.excluirAluno(id_aluno,id_turma);
        return String.format("Aluno %d removido com sucesso da turma %d",id_aluno,id_turma);
    }
}
