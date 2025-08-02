package com.eric.linkedrh.services;

import com.eric.linkedrh.dao.TurmaDao;
import com.eric.linkedrh.dtos.FuncionarioDto;
import com.eric.linkedrh.dtos.TurmaDto;
import com.eric.linkedrh.models.FuncionarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TurmaService {

    @Autowired
    private TurmaDao turmaDao;
    
    public List<TurmaDto> getTurmas(int id_curso){
        try {
            List<TurmaDto> turma = turmaDao.findTurmaByCurso(id_curso);
            if(turma.isEmpty()){
                throw new RuntimeException("Ainda não há Turmas cadastrados para esse turma");
            }
            
            return turma;
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar turma",e);
        }
    }
    public List<FuncionarioDto> getFuncionariosByTurma(int id_turma){
        try {
            List<FuncionarioModel> funcionario = turmaDao.findByTurma(id_turma);
            if(funcionario.isEmpty()){
                throw new RuntimeException("Ainda não há Funcionarios cadastrados para esse funcionario");
            }
            return funcionario.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar funcionarios ",e);
        }
    }

    public void incluirAluno(int id,int id_turma){
        try {
            this.turmaDao.adicionarParticipantes(id,id_turma);
        }catch (RuntimeException e){
            throw new RuntimeException("Erro ao incluir aluno ",e);
        }
    }
    public void excluirAluno(int id,int id_turma){
        try{
            this.turmaDao.removerParticipantes(id,id_turma);
        }catch (RuntimeException e){
            throw new RuntimeException("Erro ao excluir aluno ",e);
        }
    }

    public void createTurma(int id_curso,TurmaDto turma){
        try {
            this.turmaDao.createTurma(id_curso,turma);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao criar turma");
        }
    }

    public void updateTurma(int id_turma,TurmaDto turma){

        Map<String, Object> update = new HashMap<>();

        update.put("inicio", turma.getInicio());
        update.put("fim", turma.getFim());
        update.put("local", turma.getLocal());
        try{
            this.turmaDao.updateTurma(id_turma,update);
        }catch (Exception e){
            throw new RuntimeException("Erro ao atualizar turma",e);
        }
    }
    public  void deleteTurma(int id_turma) {
        this.turmaDao.deleteTurma(id_turma);
    }

    public FuncionarioDto toDto(FuncionarioModel funcionario){
        return new FuncionarioDto(funcionario.getNome(),funcionario.getCpf(),funcionario.getNascimento(),funcionario.getCargo(),funcionario.getAdmissao(),funcionario.getStatus());
    }
}
