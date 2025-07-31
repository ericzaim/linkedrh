package com.eric.linkedrh.services;

import com.eric.linkedrh.dtos.CursoDto;
import com.eric.linkedrh.models.CursoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eric.linkedrh.dao.CursoDao;

@Service
public class CursoService {

    @Autowired
    private CursoDao cursoDao;

    public CursoDto getCursoById(Integer id){
        CursoModel curso = cursoDao.findOneById(id);
        if(curso == null){
            throw new RuntimeException("Curso no encontrado");
        }
        return toDto(curso);
    }

    public CursoDto toDto(CursoModel curso){
        return new CursoDto(curso.getId(),curso.getNome(),curso.getDescricao(),curso.getDuracao());
    }
}
