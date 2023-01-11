package org.springcloud.msvc.cursos;

import org.springcloud.msvc.cursos.models.Usuario;
import org.springcloud.msvc.cursos.models.entities.Curso;
import org.springcloud.msvc.cursos.models.entities.CursoUsuario;

public class TestData {

    public static Curso getCurso01() {
        return new Curso(1l, "1er Grado");
    }

    public static Curso getCurso02() {
        return new Curso(2l, "2do Grado");
    }

    public static Curso getCurso03() {
        return new Curso(3l, "3ero Grado");
    }

    public static Curso getCurso04() {
        return new Curso(4l, "4to Grado");
    }

    public static Usuario getUsuario01(){
        return new Usuario(1l, "prueba", "prueba@gmail.com", "123456");
    }

    public static Usuario getUsuario02(){
        return new Usuario(2l, "prueba2", "prueba2@gmail.com", "123456");
    }

    public static CursoUsuario getCuroUsuario(){
        return new CursoUsuario(2l, 2l);
    }
}
