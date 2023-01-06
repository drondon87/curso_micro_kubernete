package org.springcloud.msvc.usuarios;

import org.springcloud.msvc.usuarios.models.entities.Usuario;

public class TestData {

    public static Usuario getUsuario01() {
        return new Usuario(1l, "Domingo Rondon", "domingorondon@gmail.com", "qwerty");
    }

    public static Usuario getUsuario02() {
        return new Usuario(2l, "Barbara Guevara", "barbaraguevara@gmail.com", "qwerty");
    }

    public static Usuario getUsuario03() {
        return new Usuario(3l, "Dayber Rondon", "dayberrondon@gmail.com", "qwerty");
    }

    public static Usuario getUsuario04() {
        return new Usuario(4l, "Dennis Rondon", "dennisrondon@gmail.com", "qwerty");
    }
}
