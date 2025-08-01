package com.eric.linkedrh.services;

import com.eric.linkedrh.dao.FuncionarioDao;
import com.eric.linkedrh.dao.TurmaDao;
import com.eric.linkedrh.dtos.TurmaDto;
import com.eric.linkedrh.models.FuncionarioModel;
import com.eric.linkedrh.models.TurmaModel;
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
    @Autowired
    private FuncionarioDao funcionarioDao;

    public List<TurmaDto> getTurmas(int id_curso){
        try {
            List<TurmaModel> turma = turmaDao.findAll(id_curso);
            List<FuncionarioModel> funcionario = funcionarioDao.findAll(id_curso);
            if(turma.isEmpty()){
                throw new RuntimeException(String.format("Ainda não há Turmas cadastrados para esse turma"));
            }
            return turma.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar turma",e);
        }
    }


    public void createTurma(int id_curso,TurmaDto turma){
        try {
            this.turmaDao.createTurma(id_curso,turma);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao criar turma");
        }
    }

    public void updateTurma(int id_curso,int id_turma,TurmaDto turma){

        Map<String, Object> update = new HashMap<>();

        update.put("inicio", turma.getInicio());
        update.put("fim", turma.getFim());
        update.put("local", turma.getLocal());
        try{
            this.turmaDao.updateTurma(id_curso,id_turma,update);
        }catch (Exception e){
            throw new RuntimeException("Erro ao atualizar turma",e);
        }
    }
    public  void deleteTurma(int id,int id_turma) {
        this.turmaDao.deleteTurma(id,id_turma);
    }

    public TurmaDto toDto(TurmaModel turma){
        return new TurmaDto(turma.getInicio(),turma.getFim(),turma.getLocal(),turma.getCurso());
    }
}
