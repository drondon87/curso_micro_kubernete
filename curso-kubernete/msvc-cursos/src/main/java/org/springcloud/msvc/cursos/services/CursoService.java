package org.springcloud.msvc.cursos.services;

import org.springcloud.msvc.commons.services.ICommonService;
import org.springcloud.msvc.cursos.models.Usuario;
import org.springcloud.msvc.cursos.models.entities.Curso;

import java.util.Optional;

public interface CursoService extends ICommonService<Curso> {

    Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);
    Optional<Curso> porIdUsuario(long id, String token);

    void eliminarCursoUsuarioById(Long id);
}
