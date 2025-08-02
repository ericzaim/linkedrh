package com.eric.linkedrh.dao;

import com.eric.linkedrh.dtos.TurmaDto;
import com.eric.linkedrh.models.FuncionarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe responsável pelas operações de acesso a dados (DAO) da entidade Turma.
 * Utiliza JdbcTemplate para executar consultas SQL no banco de dados.
 */
@Repository
public class TurmaDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Busca uma lista de turmas associadas a um curso, incluindo contagem de participantes.
     *
     * @param id_curso Identificador do curso.
     * @return Lista de objetos TurmaDto contendo dados das turmas.
     */
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

    /**
     * Busca a lista de funcionários participantes de uma turma específica.
     *
     * @param id_turma Identificador da turma.
     * @return Lista de objetos FuncionarioModel correspondentes aos participantes.
     */
    public List<FuncionarioModel> findByTurma(int id_turma){
        String  sql = "SELECT f.* FROM funcionario f INNER JOIN turma_participante tp ON f.codigo = tp.funcionario WHERE tp.turma = ? ORDER BY nome";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(FuncionarioModel.class), id_turma);
    }

    /**
     * Insere uma nova turma no banco de dados associada a um curso.
     *
     * @param id_curso Identificador do curso ao qual a turma pertence.
     * @param turma Objeto TurmaDto com os dados da turma.
     */
    public void createTurma(int id_curso,TurmaDto turma) {
        String sql = "INSERT INTO turma (inicio, fim, local,curso) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, turma.getInicio(), turma.getFim(), turma.getLocal(),id_curso);
    }

    /**
     * Atualiza campos específicos de uma turma existente.
     *
     * @param id_turma Identificador da turma a ser atualizada.
     * @param updates Mapa contendo os pares campo-valor para atualização.
     */
    public void updateTurma(int id_turma, Map<String, Object> updates){
        StringBuilder sql =  new StringBuilder("UPDATE turma SET ");
        List<Object> params = new ArrayList<>();

        updates.forEach((key,value)-> {
            sql.append(key).append(" = ?, ");
            params.add(value);
        });

        // Remove a última vírgula e espaço da query
        sql.setLength(sql.length()- 2);
        sql.append(" WHERE codigo = ?");
        params.add(id_turma);

        jdbcTemplate.update(sql.toString(),params.toArray());
    }

    /**
     * Adiciona um participante (funcionário) a uma turma.
     *
     * @param id_funcionario Identificador do funcionário a ser adicionado.
     * @param id_turma Identificador da turma.
     */
    public void adicionarParticipantes(int id_funcionario,int id_turma){
        String sql = "INSERT INTO turma_participante (turma, funcionario) VALUES (?,?)";
        jdbcTemplate.update(sql, id_turma, id_funcionario);
    }

    /**
     * Remove um participante (funcionário) de uma turma.
     *
     * @param id_funcionario Identificador do funcionário a ser removido.
     * @param id_turma Identificador da turma.
     */
    public void removerParticipantes(int id_funcionario,int id_turma){
        String sql = "DELETE FROM turma_participante WHERE turma = ? AND funcionario = ?";
        jdbcTemplate.update(sql, id_turma, id_funcionario);
    }

    /**
     * Remove uma turma e todos os seus participantes associados do banco de dados.
     *
     * @param id_turma Identificador da turma a ser deletada.
     */
    public void deleteTurma(int id_turma) {
        jdbcTemplate.update("DELETE FROM turma_participante WHERE turma = ?",id_turma);
        jdbcTemplate.update("DELETE FROM turma WHERE codigo = ?" ,id_turma);
    }

}
