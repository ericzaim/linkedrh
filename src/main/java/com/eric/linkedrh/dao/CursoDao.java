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

/**
 * Classe responsável pelas operações de acesso a dados (DAO) da entidade Curso.
 * Utiliza JdbcTemplate para executar consultas SQL no banco de dados.
 */
@Repository
public class CursoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Retorna uma lista com todos os cursos cadastrados no banco de dados.
     *
     * @return Lista de objetos CursoModel representando os cursos.
     */
    public List<CursoModel> findAll() {
        String sql =  "SELECT * FROM curso";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(CursoModel.class));
    }

    /**
     * Insere um novo curso no banco de dados.
     *
     * @param curso Objeto CursoDto contendo os dados do curso a ser criado.
     */
    public void createCurso(CursoDto curso) {
        String sql = "INSERT INTO curso (nome, descricao,duracao) VALUES (?,?,?)";
        jdbcTemplate.update(sql, curso.getNome(), curso.getDescricao(), curso.getDuracao());
    }

    /**
     * Atualiza campos específicos de um curso existente no banco de dados.
     *
     * @param id Identificador do curso a ser atualizado.
     * @param updates Mapa contendo os pares campo-valor que serão atualizados.
     */
    public void updateCurso(int id, Map<String, Object> updates){
        StringBuilder sql =  new StringBuilder("UPDATE curso SET ");
        List<Object> params = new ArrayList<>();

        updates.forEach((key,value)-> {
            sql.append(key).append(" = ?, ");
            params.add(value);
        });

        // Remove a última vírgula e espaço da query
        sql.setLength(sql.length()- 2);
        sql.append(" WHERE codigo = ?");
        params.add(id);

        jdbcTemplate.update(sql.toString(),params.toArray());
    }

    /**
     * Remove um curso do banco de dados, bem como as turmas e participantes relacionadas a ele.
     *
     * @param id Identificador do curso a ser removido.
     */
    public void deleteCurso(int id){
        jdbcTemplate.update("DELETE FROM turma_participante "+
                                "WHERE turma IN (SELECT codigo FROM turma WHERE curso = ?)", id);
        jdbcTemplate.update("DELETE FROM turma WHERE curso = ?", id);
        jdbcTemplate.update("DELETE FROM curso WHERE codigo = ?", id);
    }

}
