package com.eric.linkedrh.services;

import com.eric.linkedrh.dtos.CursoDto;
import com.eric.linkedrh.models.CursoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eric.linkedrh.dao.CursoDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela lógica de negócio relacionada à entidade Curso.
 * Fornece métodos para obter, criar, atualizar e deletar cursos,
 * além de converter entre CursoModel e CursoDto.
 */
@Service
public class CursoService {

    @Autowired
    private CursoDao cursoDao;

    /**
     * Retorna a lista de todos os cursos cadastrados convertidos para CursoDto.
     *
     * @return Lista de CursoDto
     * @throws RuntimeException caso não haja cursos cadastrados ou erro na busca
     */
    public List<CursoDto> getCursos(){
        try {
            List<CursoModel> cursos = cursoDao.findAll();
            if(cursos.isEmpty()){
                throw new RuntimeException("Ainda não há cursos cadastrados");
            }
            return cursos.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar cursos",e);
        }
    }

    /**
     * Cria um novo curso a partir dos dados do CursoDto informado.
     *
     * @param curso Dados do curso para criação
     * @return Mensagem de sucesso na criação do curso
     * @throws RuntimeException em caso de erro na criação
     */
    public String createCurso(CursoDto curso){
        try {
            this.cursoDao.createCurso(curso);
            return String.format("Curso %s cadastrado",curso.getNome());
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao criar usuário");
        }
    }

    /**
     * Atualiza um curso existente com os dados fornecidos no CursoDto.
     *
     * @param curso Dados atualizados do curso
     * @param id Identificador do curso a ser atualizado
     */
    public void updateCurso(CursoDto curso, int id){
        Map<String, Object> update = new HashMap<>();

        if (curso.getNome() != null) {
            update.put("nome", curso.getNome());
        }
        if (curso.getDescricao() != null) {
            update.put("descricao", curso.getDescricao());
        }
        if (curso.getDuracao() != 0) {
            update.put("duracao", curso.getDuracao());
        }
        try {
            this.cursoDao.updateCurso(id, update);
        } catch (RuntimeException e){
            throw new RuntimeException("Erro ao atualizar curso", e);
        }
    }


    /**
     * Deleta um curso existente com base no identificador informado.
     *
     * @param id Identificador do curso a ser deletado
     */
    public void deleteCurso(int id){
            this.cursoDao.deleteCurso(id);
    }

    /**
     * Converte um objeto CursoModel para CursoDto.
     *
     * @param curso CursoModel a ser convertido
     * @return Objeto CursoDto com dados convertidos
     */
    public CursoDto toDto(CursoModel curso){
        return new CursoDto(curso.getNome(),curso.getDescricao(),curso.getDuracao());
    }
}
