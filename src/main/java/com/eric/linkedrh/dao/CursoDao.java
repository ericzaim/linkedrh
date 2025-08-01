package com.eric.linkedrh.dao;

import com.eric.linkedrh.dtos.CursoDto;
import com.eric.linkedrh.models.CursoModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CursoDao { 
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<CursoModel> findAll() {
         String sql =  "SELECT * FROM curso";
         return jdbcTemplate.query(sql,new BeanPropertyRowMapper<CursoModel>(CursoModel.class));
    }

    public void createCurso(CursoDto curso) {
        String sql = "INSERT INTO curso (nome, descricao,duracao) VALUES (?,?,?)";
        jdbcTemplate.update(sql, curso.getNome(), curso.getDescricao(), curso.getDuracao());
    }

    public void updateCurso(int id, Map<String, Object> updates){
        StringBuilder sql =  new StringBuilder("UPDATE curso SET ");
        List<Object> params = new ArrayList<>();
        
        updates.forEach((key,value)-> {
            sql.append(key).append(" = ?, ");
            params.add(value);
        });

        //Diminui o tamanho da String da consulta em 2, ou seja o " " e a ","
        sql.setLength(sql.length()- 2);
        sql.append("WHERE codigo = ?");
        params.add(id);
        
        jdbcTemplate.update(sql.toString(),params.toArray());
    }

    public void deleteCurso(int id){
        jdbcTemplate.update("DELETE FROM curso WHERE codigo = ?", id);
    }

}
