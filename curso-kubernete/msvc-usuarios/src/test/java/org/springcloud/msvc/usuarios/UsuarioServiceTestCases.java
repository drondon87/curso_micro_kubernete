package org.springcloud.msvc.usuarios;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springcloud.msvc.usuarios.models.entities.Usuario;
import org.springcloud.msvc.usuarios.repositories.UsuarioRepository;
import org.springcloud.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class UsuarioServiceTestCases {

    @MockBean
    UsuarioRepository repository;

    @Autowired
    UsuarioService service;

    @DisplayName("Test: Listar Usuarios")
    @Test
    void testFindUsuarios_returnUsuarios() {

        // Given
        Iterable<Usuario> usuarios = Arrays.asList(TestData.getUsuario01(), TestData.getUsuario02(), TestData.getUsuario03(), TestData.getUsuario04());
        when(repository.findAll()).thenReturn(usuarios);

        // When
        List<Usuario> expectedList = service.listarUsuarios();

        assertAll(() -> {
            assertEquals(4, expectedList.size(), () -> "No trajo la misma cantidad de usuarios");
        }, () -> {
            assertFalse(expectedList.isEmpty(), () -> "La lista no puede estar vacia");
        }, () -> {
            assertTrue(expectedList.contains(TestData.getUsuario03()), () -> "La lista no tiene el usuario buscadd");
        }, () -> {
            verify(repository, times(1)).findAll();
        });
    }

    @DisplayName("Test: Buscar Usuario por Id")
    @Test
    void testFindUsuarioById_returnUsuario() {

        // Given
        Long usuarioId = 2L;
        when(repository.findById(anyLong())).thenReturn(Optional.of(TestData.getUsuario02()));

        // When
        Optional<Usuario> expected = service.getUsuarioById(usuarioId);

        // Then
        assertAll(() -> {
            assertNotNull(expected.get(), () -> "El usuario no puede ser nulo");
        }, () -> {
            assertEquals("Barbara Guevara", expected.get().getNombre(), () -> "No es el usuario esperado");
        }, () -> {
            verify(repository, times(1)).findById(anyLong());
        });
    }

    @DisplayName("Test: Guardar Usuario")
    @Test
    void testSaveUsuario_returnUsuario() {

        // Given
        Usuario userCarlos = new Usuario(null, "Carlos", "carlos@gmail.com", "12345");

        when(repository.save(any())).then(invocation -> {
            Usuario c = invocation.getArgument(0);
            c.setId(5L);
            return c;
        });

        // When
        Usuario expected = service.guardarUsuario(userCarlos);

        // Then
        assertAll(() -> {
            assertEquals("Carlos", expected.getNombre(), () -> "Los Nombres no son iguales");
        }, () -> {
            assertEquals(5, expected.getId(), () -> "Los ids no son iguales");
        }, () -> {
            assertEquals("carlos@gmail.com", expected.getEmail(), () -> "Los emails no son iguales");
        }, () -> {
            verify(repository, times(1)).save(any());
        });
    }

    @DisplayName("Test: Eliminar Usuario")
    @Test
    void testDeleteUsuario() {

        // Given
        doNothing().when(repository).deleteById(anyLong());

        // When
        service.eliminarUsuario(3L);

        // Then
        verify(repository, times(1)).deleteById(anyLong());


    }

}
