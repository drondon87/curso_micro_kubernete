package org.springcloud.msvc.cursos.repositories;

import org.springcloud.msvc.cursos.models.entities.Curso;
import org.springframework.data.repository.CrudRepository;

public interface CursoRepository extends CrudRepository<Curso, Long> {
}
