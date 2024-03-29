package org.springcloud.msvc.usuarios.services;

import feign.FeignException;
import org.springcloud.msvc.commons.services.CommonServiceImpl;
import org.springcloud.msvc.usuarios.client.CursoClientRest;
import org.springcloud.msvc.usuarios.models.entities.Usuario;
import org.springcloud.msvc.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl extends CommonServiceImpl<Usuario, UsuarioRepository> implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoClientRest client;

    @Override
    @Transactional
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
        try {
            client.eliminarCursoUsuarioPorId(id);
        } catch (FeignException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarByEmail(String email) {
        return usuarioRepository.porEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarPorIds(Iterable<Long> ids) {
        return (List<Usuario>) usuarioRepository.findAllById(ids);
    }
}
