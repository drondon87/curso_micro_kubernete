package org.springcloud.msvc.cursos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springcloud.msvc.cursos.models.entities.Curso;
import org.springcloud.msvc.cursos.repositories.CursoRepository;
import org.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class CursoServiceTestCases {

    @MockBean
    CursoRepository repository;

    @Autowired
    CursoService service;

    @DisplayName("Test: Listar Cursos")
    @Test
    void testFindCursos_returnCursos() {

        // Given
        Iterable<Curso> cursos = Arrays.asList(TestData.getCurso01(), TestData.getCurso02(), TestData.getCurso03(), TestData.getCurso04());
        when(repository.findAll()).thenReturn(cursos);

        // When
        List<Curso> expectedList = service.listarCursos();

        assertAll(() -> {
            assertEquals(4, expectedList.size(), () -> "No trajo la misma cantidad de cursos");
        }, () -> {
            assertFalse(expectedList.isEmpty(), () -> "La lista no puede estar vacia");
        }, () -> {
            assertTrue(expectedList.contains(TestData.getCurso03()), () -> "La lista no tiene el curso buscado");
        }, () -> {
            verify(repository, times(1)).findAll();
        });
    }

}
