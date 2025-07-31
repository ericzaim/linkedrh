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
         String sql =  String.format("SELECT * FROM cursos");
         return jdbcTemplate.query(sql,new BeanPropertyRowMapper<CursoModel>(CursoModel.class));
    }

    public void createCurso(CursoDto curso) {
        String sql = String.format("INSERT INTO cursos (nome, descricao,duracao) VALUES (%s,%s,%d)",curso.getNome(),curso.getDescricao(),curso.getDuracao());
        jdbcTemplate.update(sql);
    }

    public void updateCurso(int id, Map<String, Object> updates){
        StringBuilder sql =  new StringBuilder("UPDATE cursos SET ");
        List<Object> params = new ArrayList<>();
        
        updates.forEach((key,value)-> {
            sql.append(key).append(" = ?, ");
            params.add(value);
    });

        sql.setLength(sql.length()- 2);
        sql.append("WHERE id = ?");
        params.add(id);
        
        jdbcTemplate.update(sql.toString(),params.toArray());
    }

    
}
