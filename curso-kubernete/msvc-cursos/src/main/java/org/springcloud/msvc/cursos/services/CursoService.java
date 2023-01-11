package org.springcloud.msvc.cursos.services;

import org.springcloud.msvc.cursos.models.Usuario;
import org.springcloud.msvc.cursos.models.entities.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> listarCursos();

    Optional<Curso> obtenerCursoById(Long id);

    Curso guardarCurso(Curso curso);

    void eliminarCurso(Long id);

    Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);
    Optional<Curso> porIdUsuario(long id);

    void eliminarCursoUsuarioById(Long id);
}
