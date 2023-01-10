package org.springcloud.msvc.usuarios;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springcloud.msvc.usuarios.models.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioControllerTestCases {

    @Autowired
    private TestRestTemplate client;

    private ObjectMapper objectMapper;

    @LocalServerPort
    private int puerto;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @DisplayName("Test: Detalle Usuario")
    @Test
    @Order(1)
    void testDetalleUsuario_returnUsuario() {
        ResponseEntity<Usuario> response = client.getForEntity(crearUri("/api/usuarios/3"), Usuario.class);
        Usuario usuario = response.getBody();

        assertAll(() -> {
            assertEquals(HttpStatus.OK, response.getStatusCode(), () -> "El Status de la respuesta no es igual");
        }, () -> {
            assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), () -> "El content-type no es igual");
        }, () -> {
            assertNotNull(usuario);
        }, () -> {
            assertEquals(3l, usuario.getId(), () -> "El id no es correcto");
        }, () -> {
            assertEquals("Dayber Rondon", usuario.getNombre(), () -> "El usuario es incorrecto");
        }, () -> {
            assertEquals("dayberrondon@gmail.com", usuario.getEmail(), () -> "Los email no son iguales");
        });
    }

    @DisplayName("Test: Detalle Usuario no Existente")
    @Test
    @Order(2)
    void testDetalleUsuario_returnUsuarioNoExistente() {
        ResponseEntity<Usuario> response = client.getForEntity(crearUri("/api/usuarios/5"), Usuario.class);
        Usuario usuario = response.getBody();

        assertAll(() -> {
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), () -> "El Status de la respuesta no es igual");
        }, () -> {
            assertNull(response.getHeaders().getContentType(), () -> "El content-type no es igual");
        }, () -> {
            assertNull(usuario, () -> "El usuario no debe Existir");
        });
    }

    @DisplayName("Test: Listar Usuarios")
    @Test
    @Order(3)
    void testListarUsuarios_returnUsuarios() {
        ResponseEntity<Usuario[]> response = client.getForEntity(crearUri("/api/usuarios"), Usuario[].class);
        List<Usuario> usuarios = Arrays.asList(response.getBody());

        assertAll(() -> {
            assertEquals(HttpStatus.OK, response.getStatusCode(), () -> "El Status de la respuesta no es igual");
        }, () -> {
            assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), () -> "El content-type no es igual");
        }, () -> {
            assertEquals(1l, usuarios.get(0).getId(), () -> "El id no es correcto");
        }, () -> {
            assertEquals("Domingo Rondon", usuarios.get(0).getNombre(), () -> "El nombre de usuario es incorrecto");
        }, () -> {
            assertEquals("domingorondon@gmail.com", usuarios.get(0).getEmail(), () -> "Los emails no son iguales");
        }, () -> {
            assertEquals(4, usuarios.size(), () -> "La cantidad de usuarios no es igual");
        });
    }

    @DisplayName("Test: Guardar Usuario")
    @Test
    @Order(4)
    void testGuardarUsuario() {
        Usuario usuario = new Usuario(null, "Pastor", "pastor@gmail.com", "12345678");

        ResponseEntity<Usuario> response = client.postForEntity(crearUri("/api/usuarios"), usuario, Usuario.class);
        Usuario expected = response.getBody();

        assertAll(() -> {
            assertEquals(HttpStatus.CREATED, response.getStatusCode(), () -> "El Status de la respuesta no es igual");
        }, () -> {
            assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), () -> "El content-type no es igual");
        }, () -> {
            assertNotNull(expected, () -> "El Usuario debe existir");
        }, () -> {
            assertEquals(5L, expected.getId(), () -> "El id no es correcto");
        }, () -> {
            assertEquals("Pastor", expected.getNombre(), () -> "El Nombre es incorrecta");
        }, () -> {
            assertEquals("pastor@gmail.com", expected.getEmail(), () -> "Los Emails no son iguales");
        });
    }

    @DisplayName("Test: Editar Usuario")
    @Test
    @Order(5)
    void testEditarUsuario() {
        Usuario usuario = new Usuario(null, "Pastor Oviedo", "pastoroviedo@gmail.com", "querty");

        client.put(crearUri("/api/usuarios/5"), usuario, Usuario.class);
        ResponseEntity<Usuario> response = client.getForEntity(crearUri("/api/usuarios/5"), Usuario.class);
        Usuario expected = response.getBody();

        assertAll(() -> {
            assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), () -> "El content-type no es igual");
        }, () -> {
            assertNotNull(expected, () -> "El Usuario debe existir");
        }, () -> {
            assertEquals(5L, expected.getId(), () -> "El id no es correcto");
        }, () -> {
            assertEquals("Pastor Oviedo", expected.getNombre(), () -> "El Nombre es incorrecta");
        }, () -> {
            assertEquals("pastoroviedo@gmail.com", expected.getEmail(), () -> "Los Emails no son iguales");
        });
    }

    @DisplayName("Test: Eliminar Usuario")
    @Test
    @Order(6)
    void testEliminarUsuario() {
        ResponseEntity<Usuario[]> response = client.getForEntity(crearUri("/api/usuarios"), Usuario[].class);
        List<Usuario> usuarios = Arrays.asList(response.getBody());
        assertEquals(5, usuarios.size(), () -> "La cantidad de usuarios no es la correcta");

        ResponseEntity<Void> exchange = client.exchange(crearUri("/api/usuarios/5"), HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, exchange.getStatusCode());
        assertFalse(exchange.hasBody());

        response = client.getForEntity(crearUri("/api/usuarios"), Usuario[].class);
        usuarios = Arrays.asList(response.getBody());
        assertEquals(4, usuarios.size(), () -> "La cantidad de usuarios no es la correcta");

        ResponseEntity<Usuario> responseDetalle = client.getForEntity(crearUri("/api/usuarios/5"), Usuario.class);
        assertEquals(HttpStatus.NOT_FOUND, responseDetalle.getStatusCode());
        assertFalse(responseDetalle.hasBody());
    }

    @DisplayName("Test: Eliminar Usuari no Existente")
    @Test
    @Order(7)
    void testEliminarUsuarioNoExistente() {
        ResponseEntity<Void> exchange = client.exchange(crearUri("/api/usuarios/5"), HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NOT_FOUND, exchange.getStatusCode());
        assertFalse(exchange.hasBody());
    }

    @DisplayName("Test: Guardar Usuario con Correo Existente")
    @Test
    @Order(8)
    void testGuardarUsuarioWithEmailExist() {
        Usuario usuario = new Usuario(null, "Pastor", "dayberrondon@gmail.com", "12345678");

        ResponseEntity<Usuario> response = client.postForEntity(crearUri("/api/usuarios"), usuario, Usuario.class);
        Usuario expected = response.getBody();

        assertAll(() -> {
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), () -> "El Status de la respuesta no es igual");
        }, () -> {
            assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), () -> "El content-type no es igual");
        }, () -> {
            assertNotNull(expected, () -> "El Usuario no debe existir");
        });
    }

    private String crearUri(String uri) {
        return "http://localhost:" + puerto + uri;
    }
}
