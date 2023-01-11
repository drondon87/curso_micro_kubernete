package org.springcloud.msvc.cursos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springcloud.msvc.cursos.models.entities.Curso;
import org.springcloud.msvc.cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CursoRepositoyTestCases {

    @Autowired
    CursoRepository repository;

    @DisplayName("Test: Buscar Cursos")
    @Test
    void testFindAllCursos_returnCursos() {
        // When
        Iterable<Curso> cursos = repository.findAll();
        List<Curso> expected = new ArrayList<>();
        cursos.forEach(expected::add);

        // Then
        assertAll(() -> {
            assertFalse(expected.isEmpty(), () -> "Los Cursos no pueden estar vacios");
        }, () -> {
            assertEquals(4, expected.size(), () -> "La cantidad de cursos no es igual a la esperada");
        });
    }

    @DisplayName("Test: Buscar Curso por Id Existente")
    @Test
    void testFindCursoByIdExistente_returnCurso() {
        // Given
        Curso cursoTest = new Curso();
        cursoTest.setId(3L);
        cursoTest.setNombre("3ero Grado");

        // When
        Optional<Curso> expected = repository.findById(3L);

        // Then
        assertAll(() -> {
            assertTrue(expected.isPresent(), () -> "El curso debe existir");
        }, () -> {
            assertEquals(cursoTest.getNombre(), expected.get().getNombre(), () -> "Los cursos no son iguales");
        });
    }

    @DisplayName("Test: Buscar Curso por Id No Existente")
    @Test
    void testFindCursoByNoneExistingId_returnException() {
        // When
        Optional<Curso> curso = repository.findById(7L);

        // Then
        assertAll(() -> {
            assertThrows(NoSuchElementException.class, curso::orElseThrow);
        }, () -> {
            assertFalse(curso.isPresent(), () -> "El curso no debería de existir");
        });
    }

    @DisplayName("Test: Guardar Curso")
    @Test
    void testSaveCurso_returnCursoSaved() {
        // Given
        Curso cursoToSave = new Curso();
        cursoToSave.setNombre("5to Grado");

        // When
        Curso expected = repository.save(cursoToSave);

        Iterable<Curso> cursos = repository.findAll();
        List<Curso> expectedList = new ArrayList<>();
        cursos.forEach(expectedList::add);

        // Then
        assertAll(() -> {
            assertNotNull(expected.getId(), () -> "El id no debe estar nulo");
        }, () -> {
            assertEquals(cursoToSave.getNombre(), expected.getNombre(), () -> "Los nombres no son iguales");
        }, () -> {
            assertEquals(5, expectedList.size(), () -> "La lista no tiene la cantidad de cursos esperado");
        });
    }

    @DisplayName("Test: Eliminar Curso")
    @Test
    void testDeleteCurso() {
        // Given
        Curso curso = repository.findById(2l).orElseThrow();

        // When
        repository.delete(curso);

        Iterable<Curso> cursos = repository.findAll();
        List<Curso> expectedList = new ArrayList<>();
        cursos.forEach(expectedList::add);

        // Then
        assertAll(() -> {
            assertThrows(NoSuchElementException.class, () -> repository.findById(2l).orElseThrow());
        }, () -> {
            assertEquals(3, expectedList.size(), () -> "La cantidad de cursos no son iguales");
        });
    }
}
