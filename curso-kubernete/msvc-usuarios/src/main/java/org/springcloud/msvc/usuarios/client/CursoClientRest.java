package org.springcloud.msvc.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos", url = "localhost:8002/api/cursos")
public interface CursoClientRest {

    @DeleteMapping("/eliminar-curso-usuario/{id}")
    void eliminarCursoUsuarioPorId(@PathVariable Long id);
}