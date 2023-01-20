package org.springcloud.msvc.usuarios.services;

import org.springcloud.msvc.commons.services.ICommonService;
import org.springcloud.msvc.usuarios.models.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService  extends ICommonService<Usuario> {
    void deleteById(Long id);

    Optional<Usuario> buscarByEmail(String email);

    boolean existeEmail(String email);

    List<Usuario> listarPorIds(Iterable<Long> ids);

}
