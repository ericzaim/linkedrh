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

@Service
public class CursoService {

    @Autowired
    private CursoDao cursoDao;

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

    public String createCurso(CursoDto curso){
        try {
            this.cursoDao.createCurso(curso);
            return String.format("Curso %s cadastrado",curso.getNome());
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao criar usuário");
        }
    }

    public void updateCurso(CursoDto curso, int id){

        Map<String, Object> update = new HashMap<>();

        update.put("nome", curso.getNome());
        update.put("descricao", curso.getDescricao());
        update.put("duracao", curso.getDuracao());

        this.cursoDao.updateCurso(id,update);
    }
    public  void deleteCurso(int id){
        this.cursoDao.deleteCurso(id);
    }
    

    public CursoDto toDto(CursoModel curso){
        return new CursoDto(curso.getNome(),curso.getDescricao(),curso.getDuracao());
    }
}
