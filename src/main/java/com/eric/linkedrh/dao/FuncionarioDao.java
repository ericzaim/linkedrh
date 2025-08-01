package com.eric.linkedrh.dao;

import com.eric.linkedrh.dtos.FuncionarioDto;
import com.eric.linkedrh.models.FuncionarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class FuncionarioDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<FuncionarioModel> findAll(int id_funcionario){
        String  sql = "SELECT * FROM funcionario WHERE codigo = ? ORDER BY nome";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(FuncionarioModel.class), id_funcionario);
    }

    public List<FuncionarioDto> findByTurma(int id_turma,int id_funcionario){
        String sql = "SELECT funcionario.* FROM funcionario INNNER JOIN turma_participante T WHERE turma_participante.turma = ? and funcionario = ?  ORDER BY nome";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(FuncionarioDto.class), id_turma,id_funcionario);
    }

    public void createFuncionario(FuncionarioDto funcionario) {
        String sql = "INSERT INTO funcionario (nome, cpf, nascimento, cargo, admissao, status) VALUES (?,?,?,?,?,?)";
        jdbcTemplate.update(sql, funcionario.getNome(), funcionario.getCpf(), funcionario.getNascimento(),funcionario.getCargo(), funcionario.getAdmissao(), funcionario.getStatus());
    }

    public void updateFuncionario(int id_funcionario, Map<String, Object> updates){
        StringBuilder sql =  new StringBuilder("UPDATE funcionario SET ");
        List<Object> params = new ArrayList<>();

        updates.forEach((key,value)-> {
            sql.append(key).append(" = ?, ");
            params.add(value);
        });

        //Diminui o tamanho da String da consulta em 2, ou seja o " " e a ","
        sql.setLength(sql.length()- 2);
        sql.append("WHERE codigo = ?");
        params.add(id_funcionario);

        jdbcTemplate.update(sql.toString(),params.toArray());
    }

    public void deleteFuncionario(int id_funcionario){
        jdbcTemplate.update("DELETE FROM funcionario WHERE codigo = ?", id_funcionario);
    }

}
