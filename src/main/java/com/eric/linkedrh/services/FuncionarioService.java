package com.eric.linkedrh.services;

import com.eric.linkedrh.dao.FuncionarioDao;
import com.eric.linkedrh.dtos.FuncionarioDto;
import com.eric.linkedrh.models.FuncionarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioDao funcionarioDao;

    public List<FuncionarioDto> getAllFuncionarios(){
        try {
            List<FuncionarioModel> funcionario = funcionarioDao.findAll();
            return funcionario.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar funcionarios ",e);
        }
    }

    public List<FuncionarioDto> getFuncionariosByTurma(int id_turma){
        try {
            List<FuncionarioModel> funcionario = funcionarioDao.findByTurma(id_turma);
            if(funcionario.isEmpty()){
                throw new RuntimeException(String.format("Ainda não há Funcionarios cadastrados para esse funcionario"));
            }
            return funcionario.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar funcionarios ",e);
        }
    }

    public FuncionarioDto toDto(FuncionarioModel funcionario){
        return new FuncionarioDto(funcionario.getNome(),funcionario.getCpf(),funcionario.getNascimento(),funcionario.getCargo(),funcionario.getAdmissao(),funcionario.getStatus());
    }
}
