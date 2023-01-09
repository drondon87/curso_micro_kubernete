package org.springcloud.msvc.cursos.services;

import org.springcloud.msvc.cursos.entities.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> listarCursos();

    Optional<Curso> obtenerCursoById(Long id);

    Curso guardarCurso(Curso curso);

    void eliminarCurso(Long id);
}
