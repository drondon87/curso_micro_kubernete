package org.springcloud.msvc.usuarios;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springcloud.msvc.usuarios.models.entities.Usuario;
import org.springcloud.msvc.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@DataJpaTest
public class UsuarioRepositoryTestCases {

    @Autowired
    UsuarioRepository repository;

    @DisplayName("Test: Buscar Usuarios")
    @Test
    void testFindAllUsuarios_returnUsuarios() {
        // When
        Iterable<Usuario> usuarios = repository.findAll();
        List<Usuario> expected = new ArrayList<>();
        usuarios.forEach(expected::add);

        // Then
        assertAll(() -> {
            assertFalse(expected.isEmpty(), () -> "Los usuarios no pueden estar vacios");
        }, () -> {
            assertEquals(4, expected.size(), () -> "La cantidad de usuarios no es igual a la esperada");
        });
    }

    @DisplayName("Test: Buscar Usuario por Id Existente")
    @Test
    void testFindUsuarioByIdExistente_returnUsuario() {
        // Given
        Usuario userTest = new Usuario(2L, "Barbara Guevara", "barbaraguevara@gmail.com", "qwerty");

        // When
        Optional<Usuario> expected = repository.findById(2L);

        // Then
        assertAll(() -> {
            assertTrue(expected.isPresent(), () -> "El usuario debe existir");
        }, () -> {
            assertEquals("Barbara Guevara", expected.get().getNombre(), () -> "Los nombres no son iguales");
        }, () -> {
            assertEquals("barbaraguevara@gmail.com", expected.get().getEmail(), () -> "Los mails no son iguales");
        }, () -> {
            assertEquals(userTest, expected.get(), () -> "Los usuarios no son iguales");
        });
    }

    @DisplayName("Test: Buscar Usuario por Id No Existente")
    @Test
    void testFindUsuarioByNonExistingId_returnException() {
        // When
        Optional<Usuario> usuario = repository.findById(7L);

        // Then
        assertAll(() -> {
            assertThrows(NoSuchElementException.class, usuario::orElseThrow);
        }, () -> {
            assertFalse(usuario.isPresent(), () -> "El usuario no debería de existir");
        });
    }

    @DisplayName("Test: Guardar Usuario")
    @Test
    void testSaveUsuario_returnUsuarioSaved() {
        // Given
        Usuario userToSave = new Usuario(null, "Pedro", "pedro@gmail.com", "qwerty");

        // When
        Usuario expected = repository.save(userToSave);

        Iterable<Usuario> usuarios = repository.findAll();
        List<Usuario> expectedList = new ArrayList<>();
        usuarios.forEach(expectedList::add);

        // Then
        assertAll(() -> {
            assertNotNull(expected.getId(), () -> "El id no debe estar nulo");
        }, () -> {
            assertEquals(userToSave.getNombre(), expected.getNombre(), () -> "Los nombres no son iguales");
        }, () -> {
            assertEquals(5, expectedList.size(), () -> "La lista no tiene la cantidad de usuarios esperado");
        });
    }

    @DisplayName("Test: Eliminar Usuario")
    @Test
    void testDeleteUsuario() {
        // Given
        Usuario user = repository.findById(2l).orElseThrow();

        // When
        repository.delete(user);

        Iterable<Usuario> usuarios = repository.findAll();
        List<Usuario> expectedList = new ArrayList<>();
        usuarios.forEach(expectedList::add);

        // Then
        assertAll(() -> {
            assertThrows(NoSuchElementException.class, () -> repository.findById(2l).orElseThrow());
        }, () -> {
            assertEquals(3, expectedList.size(), () -> "La cantidad de usuarios no son iguales");
        });
    }

    @DisplayName("Test: Buscar Usuario por Email Existente JPA")
    @Test
    void testFindUsuarioByEmailExistenteJPA_returnUsuario() {
        // Given
        String email = "dayberrondon@gmail.com";
        // When
        Optional<Usuario> expected = repository.findByEmail(email);

        // Then
        assertAll(() -> {
            assertTrue(expected.isPresent(), () -> "El usuario debe existir");
        }, () -> {
            assertEquals("Dayber Rondon", expected.get().getNombre(), () -> "Los nombres no son iguales");
        }, () -> {
            assertEquals(3, expected.get().getId(), () -> "Los id no son iguales");
        }, () -> {
            assertEquals(email, expected.get().getEmail(), () -> "Los emails no son iguales");
        });
    }

    @DisplayName("Test: Buscar Usuario por Email No Existente JPA")
    @Test
    void testFindUsuarioByNonExistingEmailJPA_returnException() {
        // Given
        String email = "dayberrondon@.com";

        // When
        Optional<Usuario> expected = repository.findByEmail(email);

        // Then
        assertAll(() -> {
            assertThrows(NoSuchElementException.class, expected::orElseThrow);
        }, () -> {
            assertFalse(expected.isPresent(), () -> "El usuario no debería de existir");
        });
    }

    @DisplayName("Test: Buscar Usuario por Email Existente Query")
    @Test
    void testFindUsuarioByEmailExistenteQuery_returnUsuario() {
        // Given
        String email = "dayberrondon@gmail.com";
        // When
        Optional<Usuario> expected = repository.porEmail(email);

        // Then
        assertAll(() -> {
            assertTrue(expected.isPresent(), () -> "El usuario debe existir");
        }, () -> {
            assertEquals("Dayber Rondon", expected.get().getNombre(), () -> "Los nombres no son iguales");
        }, () -> {
            assertEquals(3, expected.get().getId(), () -> "Los id no son iguales");
        }, () -> {
            assertEquals(email, expected.get().getEmail(), () -> "Los emails no son iguales");
        });
    }

    @DisplayName("Test: Buscar Usuario por Email No Existente Query")
    @Test
    void testFindUsuarioByNonExistingEmailQuery_returnException() {
        // Given
        String email = "dayberrondon@.com";

        // When
        Optional<Usuario> expected = repository.porEmail(email);

        // Then
        assertAll(() -> {
            assertThrows(NoSuchElementException.class, expected::orElseThrow);
        }, () -> {
            assertFalse(expected.isPresent(), () -> "El usuario no debería de existir");
        });
    }

    @DisplayName("Test: Buscar Usuario por Email Existente Boolean")
    @Test
    void testFindUsuarioByEmailExistenteBoolean_returnTrue() {
        // Given
        String email = "dayberrondon@gmail.com";
        // When
        boolean expected = repository.existsByEmail(email);

        // Then
        assertAll(() -> {
            assertTrue(expected, () -> "El usuario debe existir");
        });
    }

    @DisplayName("Test: Buscar Usuario por Email No Existente Boolean")
    @Test
    void testFindUsuarioByEmailNoExistenteBoolean_returnFalse() {
        // Given
        String email = "dayberrondon.com";
        // When
        boolean expected = repository.existsByEmail(email);

        // Then
        assertAll(() -> {
            assertFalse(expected, () -> "El usuario debe existir");
        });
    }
}
