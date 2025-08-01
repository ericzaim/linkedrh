package com.eric.linkedrh.dao;

import com.eric.linkedrh.dtos.TurmaDto;
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

    public List<TurmaDto> findTurmaByCurso(int id_curso){
        String  sql ="SELECT "+
                        "t.inicio, "+
                        "t.fim, "+
                        "t.local, "+
                        "t.curso, "+
                        "COUNT(tp.funcionario) AS participantes "+
                    "FROM turma t "+
                    "LEFT JOIN turma_participante tp ON t.codigo = tp.turma "+
                    "WHERE t.curso = ? "+
                    "GROUP BY t.codigo, t.inicio, t.fim, t.local, t.curso "+
                    "ORDER BY t.inicio,t.fim";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(TurmaDto.class), id_curso);
    }

    public void createTurma(int id_curso,TurmaDto turma) {
        String sql = "INSERT INTO turma (inicio, fim, local,curso) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, turma.getInicio(), turma.getFim(), turma.getLocal(),id_curso);
    }

    public void updateTurma(int id_turma, Map<String, Object> updates){
        StringBuilder sql =  new StringBuilder("UPDATE turma SET ");
        List<Object> params = new ArrayList<>();

        updates.forEach((key,value)-> {
            sql.append(key).append(" = ?, ");
            params.add(value);
        });

 //Diminui o tamanho da String da consulta em 2, ou seja o " " e a ","
        sql.setLength(sql.length()- 2);
        sql.append("WHERE codigo = ?");
        params.add(id_turma);

        jdbcTemplate.update(sql.toString(),params.toArray());
    }

    public void adicionarParticipantes(int id_funcionario,int id_turma){
        String sql = "INSERT INTO turma_participante (turma, funcionario) VALUES (?,?)";
        jdbcTemplate.update(sql, id_turma, id_funcionario);
    }

    public void deleteTurma(int id_turma) {
        String sql = "DELETE FROM turma WHERE codigo = ?";
        jdbcTemplate.update(sql,id_turma);
    }


}
