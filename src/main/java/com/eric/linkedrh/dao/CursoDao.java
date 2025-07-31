package com.eric.linkedrh.dao;

import com.eric.linkedrh.models.CursoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CursoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public CursoModel findOneById(int id) {
        String sql =  String.format("SELECT * FROM cursos WHERE id=%d", id);
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CursoModel.class), id);
    }

//    !!!Alterar consulta!!!
    public int salvar(CursoModel curso) {
        String sql = "INSERT INTO cursos (nome, descricao) VALUES (?, ?)";
        return jdbcTemplate.update(sql, curso.getNome(), curso.getDescricao());
    }
}
