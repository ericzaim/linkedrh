package com.eric.linkedrh.services;

import com.eric.linkedrh.dao.FuncionarioDao;
import com.eric.linkedrh.dao.FuncionarioDao;
import com.eric.linkedrh.dtos.FuncionarioDto;
import com.eric.linkedrh.models.FuncionarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioDao funcionarioDao;

    public List<FuncionarioDto> getFuncionarios(int id_funcionario){
        try {
            List<FuncionarioModel> funcionario = funcionarioDao.findAll(id_funcionario);
            if(funcionario.isEmpty()){
                throw new RuntimeException(String.format("Ainda não há Funcionarios cadastrados para esse funcionario"));
            }
            return funcionario.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar funcionario",e);
        }
    }

    public void createFuncionario(FuncionarioDto funcionario){
        try {
            this.funcionarioDao.createFuncionario(funcionario);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao criar funcionario");
        }
    }

    public void updateFuncionario(int id_funcionario,int id_turma,FuncionarioDto funcionario){

        Map<String, Object> update = new HashMap<>();

        update.put("nome", funcionario.getNome());
        update.put("cpf", funcionario.getCpf());
        update.put("nascimento", funcionario.getNascimento());
        update.put("cargo", funcionario.getCargo());
        update.put("admissao", funcionario.getAdmissao());
        update.put("status", funcionario.getStatus());

        try{
            this.funcionarioDao.updateFuncionario(id_funcionario,update);
        }catch (Exception e){
            throw new RuntimeException("Erro ao atualizar funcionario",e);
        }
    }
    public  void deleteFuncionario(int id_funcionario) {
        this.funcionarioDao.deleteFuncionario(id_funcionario);
    }

    public FuncionarioDto toDto(FuncionarioModel funcionario){
        return new FuncionarioDto(funcionario.getNome(),funcionario.getCpf(),funcionario.getNascimento(),funcionario.getCargo(),funcionario.getAdmissao(),funcionario.getStatus());
    }
}
