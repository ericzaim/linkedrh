package com.eric.linkedrh.dao;

import com.eric.linkedrh.dtos.TurmaDto;
import com.eric.linkedrh.models.TurmaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TurmaDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<TurmaModel> findAll(int id_curso){
        String  sql = "SELECT * FROM turma WHERE curso = ? ORDER BY fim";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(TurmaModel.class), id_curso);
    }
    public void createTurma(int id_curso,TurmaDto turma) {
        String sql = "INSERT INTO turma (inicio, fim, local,curso) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, turma.getInicio(), turma.getFim(), turma.getLocal(),id_curso);
    }

    public void updateTurma(int id_curso,int id_turma, Map<String, Object> updates){
        StringBuilder sql =  new StringBuilder("UPDATE turma SET ");
        List<Object> params = new ArrayList<>();

        updates.forEach((key,value)-> {
            sql.append(key).append(" = ?, ");
            params.add(value);
        });

        //Diminui o tamanho da String da consulta em 2, ou seja o " " e a ","
        sql.setLength(sql.length()- 2);
        sql.append("WHERE codigo = ? AND curso = ?");
        params.add(id_turma,id_curso);

        jdbcTemplate.update(sql.toString(),params.toArray());
    }

    public void deleteTurma(int id,int id_turma) {
        String sql = "DELETE FROM turma WHERE codigo = ? and curso = ?";
        jdbcTemplate.update(sql,id_turma,id);
    }

}
