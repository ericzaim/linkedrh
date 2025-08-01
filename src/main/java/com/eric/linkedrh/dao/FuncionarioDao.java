package com.eric.linkedrh.dao;

import com.eric.linkedrh.models.FuncionarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FuncionarioDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<FuncionarioModel> findAll(){
        String sql = "SELECT * FROM funcionario";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<FuncionarioModel>());
    }

    public List<FuncionarioModel> findByTurma(int id_turma){
        String  sql = "SELECT * FROM funcionario WHERE codigo = ? ORDER BY nome";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(FuncionarioModel.class), id_turma);
    }
}
