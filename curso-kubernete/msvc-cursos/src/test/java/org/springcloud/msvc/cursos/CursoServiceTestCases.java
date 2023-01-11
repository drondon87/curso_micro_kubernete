package org.springcloud.msvc.cursos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springcloud.msvc.cursos.clients.UsuarioClientRest;
import org.springcloud.msvc.cursos.models.Usuario;
import org.springcloud.msvc.cursos.models.entities.Curso;
import org.springcloud.msvc.cursos.models.entities.CursoUsuario;
import org.springcloud.msvc.cursos.repositories.CursoRepository;
import org.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class CursoServiceTestCases {

    @MockBean
    CursoRepository repository;

    @MockBean
    UsuarioClientRest client;

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

    @DisplayName("Test: Buscar Curso por Id")
    @Test
    void testFindCursoById_returnCurso() {

        // Given
        Long cursoId = 2L;
        when(repository.findById(anyLong())).thenReturn(Optional.of(TestData.getCurso02()));

        // When
        Optional<Curso> expected = service.obtenerCursoById(cursoId);

        // Then
        assertAll(() -> {
            assertNotNull(expected.get(), () -> "El curso no puede ser nulo");
        }, () -> {
            assertEquals("2do Grado", expected.get().getNombre(), () -> "No es el curso esperado");
        }, () -> {
            verify(repository, times(1)).findById(anyLong());
        });
    }

    @DisplayName("Test: Guardar Curso")
    @Test
    void testSaveCurso_returnCurso() {

        // Given
        Curso cursoToSave = new Curso(null, "7mo grado");

        when(repository.save(any())).then(invocation -> {
            Curso c = invocation.getArgument(0);
            c.setId(7L);
            return c;
        });

        // When
        Curso expected = service.guardarCurso(cursoToSave);

        // Then
        assertAll(() -> {
            assertEquals("7mo grado", expected.getNombre(), () -> "Los Nombres no son iguales");
        }, () -> {
            assertEquals(7, expected.getId(), () -> "Los ids no son iguales");
        }, () -> {
            verify(repository, times(1)).save(any());
        });
    }

    @DisplayName("Test: Eliminar Curso")
    @Test
    void testDeleteCurso() {

        // Given
        doNothing().when(repository).deleteById(anyLong());

        // When
        service.eliminarCurso(3L);

        // Then
        verify(repository, times(1)).deleteById(anyLong());
    }

    @DisplayName("Test: Asignar Usuario")
    @Test
    void testAsignarUsuario_returnCurso() {

        // Given
        Usuario usuario = TestData.getUsuario01();
        Long cursoId = 2L;
        CursoUsuario cursoUsuario = TestData.getCuroUsuario();
        Curso curso = TestData.getCurso02();
        curso.setUsuarios(Arrays.asList(usuario));
        curso.addCursoUsuario(cursoUsuario);
        Usuario usuarioMvc = new Usuario(2l, "prueba2", "prueba2@gmail.com", "123456");

        when(repository.findById(cursoId)).thenReturn(Optional.of(curso));
        when(client.detalle(usuario.getId())).thenReturn(usuarioMvc);
        when(repository.save(curso)).thenReturn(curso);

        // When
        Optional<Usuario> expected = service.asignarUsuario(usuario, cursoId);

        // Then
        assertAll(() -> {
            assertNotNull(expected.get(), () -> "El curso no puede ser nulo");
        }, () -> {
            assertEquals("prueba2", expected.get().getNombre(), () -> "No es el curso esperado");
        }, () -> {
            verify(repository, times(1)).findById(anyLong());
        }, () -> {
            verify(client, times(1)).detalle(anyLong());
        }, () -> {
            verify(repository, times(1)).save(any());
        });
    }

    @DisplayName("Test: Crear Usuario")
    @Test
    void testCrearUsuario_returnUsuario() {

        // Given
        Long cursoId = 2L;
        Usuario usuario = TestData.getUsuario01();
        usuario.setId(null);
        CursoUsuario cursoUsuario = TestData.getCuroUsuario();
        Curso curso = TestData.getCurso02();
        curso.setUsuarios(Arrays.asList(usuario));
        Usuario usuarioMvc = new Usuario(2l, "prueba2", "prueba2@gmail.com", "123456");
        curso.addCursoUsuario(cursoUsuario);

        when(repository.findById(cursoId)).thenReturn(Optional.of(curso));
        when(client.crear(usuario)).thenReturn(usuarioMvc);
        when(repository.save(curso)).thenReturn(curso);

        // When
        Optional<Usuario> expected = service.crearUsuario(usuario, cursoId);

        // Then
        assertAll(() -> {
            assertNotNull(expected.get(), () -> "El curso no puede ser nulo");
        }, () -> {
            assertEquals("prueba2", expected.get().getNombre(), () -> "No es el curso esperado");
        }, () -> {
            verify(repository, times(1)).findById(anyLong());
        }, () -> {
            verify(client, times(1)).crear(any());
        }, () -> {
            verify(repository, times(1)).save(any());
        });
    }

    @DisplayName("Test: Eliminar Usuario")
    @Test
    void testEliminarUsuario_returnCurso() {

        // Given
        Usuario usuario = TestData.getUsuario01();
        Long cursoId = 2L;
        CursoUsuario cursoUsuario = TestData.getCuroUsuario();
        Curso curso = TestData.getCurso02();
        curso.setUsuarios(Arrays.asList(usuario));
        Usuario usuarioMvc = new Usuario(2l, "prueba2", "prueba2@gmail.com", "123456");

        when(repository.findById(cursoId)).thenReturn(Optional.of(curso));
        when(client.detalle(usuario.getId())).thenReturn(usuarioMvc);
        when(repository.save(curso)).thenReturn(curso);

        // When
        Optional<Usuario> expected = service.eliminarUsuario(usuario, cursoId);

        // Then
        assertAll(() -> {
            assertNotNull(expected.get(), () -> "El curso no puede ser nulo");
        }, () -> {
            assertEquals("prueba2", expected.get().getNombre(), () -> "No es el curso esperado");
        }, () -> {
            verify(repository, times(1)).findById(anyLong());
        }, () -> {
            verify(client, times(1)).detalle(anyLong());
        }, () -> {
            verify(repository, times(1)).save(any());
        });
    }

    @DisplayName("Test: Eliminar Curso Usuario por Id")
    @Test
    void testEliminaCursoUsuarioById() {

        // Given
        doNothing().when(repository).eliminarCursoUsuarioById(anyLong());

        // When
        service.eliminarCursoUsuarioById(2L);

        // Then
        verify(repository, times(1)).eliminarCursoUsuarioById(anyLong());
    }

}
