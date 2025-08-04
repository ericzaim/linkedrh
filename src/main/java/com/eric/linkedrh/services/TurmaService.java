package com.eric.linkedrh.services;

import com.eric.linkedrh.dao.TurmaDao;
import com.eric.linkedrh.dtos.FuncionarioDto;
import com.eric.linkedrh.dtos.TurmaDto;
import com.eric.linkedrh.dtos.TurmaPostDto;
import com.eric.linkedrh.models.FuncionarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela lógica de negócio relacionada às Turmas.
 * Fornece métodos para buscar turmas, buscar funcionários por turma,
 * incluir e excluir participantes, criar, atualizar e deletar turmas,
 * além de converter FuncionarioModel para FuncionarioDto.
 */
@Service
public class TurmaService {

    @Autowired
    private TurmaDao turmaDao;

    /**
     * Retorna a lista de turmas associadas a um curso específico.
     *
     * @param id_curso Identificador do curso
     * @return Lista de TurmaDto
     * @throws RuntimeException caso não haja turmas cadastradas para o curso ou erro na busca
     */
    public List<TurmaDto> getTurmas(int id_curso){
        try{
            return turmaDao.findTurmaByCurso(id_curso);

        }catch(RuntimeException e){
            throw new RuntimeException("Não foi possível encontrar curso: " + id_curso + e);
        }
    }

    /**
     * Retorna a lista de funcionários associados a uma turma específica.
     *
     * @param inicio Data de inicio da turma
     * @param fim Data de fim da turma
     * @return Lista de FuncionarioDto
     * @throws RuntimeException caso não haja funcionários cadastrados para a turma ou erro na busca
     */
    public List<FuncionarioDto> getFuncionariosByTurma(int id_curso,LocalDate inicio, LocalDate fim){
        try{
            List<FuncionarioModel> funcionario = turmaDao.findByTurma(id_curso,inicio, fim);

            if(funcionario.isEmpty()){
                throw new RuntimeException("Ainda não há Funcionarios cadastrados para essa turma");
            }
            return funcionario.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        }catch(InternalError e){
            throw new InternalError("Erro ao buscar funcionarios ", e);
        }
    }

    /**
     * Inclui um participante (aluno) em uma turma.
     *
     * @param id_turma Identificador da turma
     * @param id_funcionario Identificador do participante (aluno)
     * @throws RuntimeException em caso de erro ao incluir o aluno
     */
    public void incluirAluno(int id_turma,int id_funcionario){
        try {
            this.turmaDao.adicionarParticipantes(id_turma,id_funcionario);
        }catch (RuntimeException e){
            throw new RuntimeException("Erro ao incluir aluno ",e);
        }
    }

    /**
     * Remove um participante (aluno) de uma turma.
     *
     * @param id Identificador do participante (aluno)
     * @param id_turma Identificador da turma
     * @throws RuntimeException em caso de erro ao excluir o aluno
     */
    public void excluirAluno(int id,int id_turma){
        try{
            this.turmaDao.removerParticipantes(id,id_turma);
        }catch (RuntimeException e){
            throw new RuntimeException("Erro ao excluir aluno ",e);
        }
    }

    /**
     * Cria uma nova turma associada a um curso.
     *
     * @param id_curso Identificador do curso
     * @param turma Dados da turma a ser criada
     * @throws RuntimeException em caso de erro na criação da turma
     */
    public void createTurma(int id_curso, TurmaPostDto turma){
        try {
            this.turmaDao.createTurma(id_curso,turma);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao criar turma");
        }
    }

    /**
     * Atualiza os dados de uma turma existente.
     *
     * @param id_turma Identificador da turma a ser atualizada
     * @param turma Dados atualizados da turma
     * @throws RuntimeException em caso de erro na atualização
     */
    public void updateTurma(int id_turma,TurmaPostDto turma){
        Map<String, Object> update = new HashMap<>();
        if (turma.getInicio() != null){
            update.put("inicio", turma.getInicio());
        }
        if (turma.getFim() != null){
            update.put("fim", turma.getFim());
        }
        if (turma.getLocal() != null) {
            update.put("local", turma.getLocal());
        }
        try{
            this.turmaDao.updateTurma(id_turma,update);
        }catch (Exception e){
            throw new RuntimeException("Erro ao atualizar turma",e);
        }
    }

    /**
     * Deleta uma turma existente.
     *
     * @param id_turma Identificador da turma a ser deletada
     */
    public void deleteTurma(int id_turma) {
        try {
            this.turmaDao.deleteTurma(id_turma);
        }catch (RuntimeException e){
            throw new RuntimeException("Erro ao deletar turma",e);
        }
    }

    /**
     * Converte um objeto FuncionarioModel para FuncionarioDto.
     *
     * @param funcionario FuncionarioModel a ser convertido
     * @return Objeto FuncionarioDto com dados convertidos
     */
    public FuncionarioDto toDto(FuncionarioModel funcionario){
        return new FuncionarioDto(funcionario.getNome(),funcionario.getCpf(),funcionario.getNascimento(),funcionario.getCargo(),funcionario.getAdmissao(),funcionario.isStatus());
    }
}