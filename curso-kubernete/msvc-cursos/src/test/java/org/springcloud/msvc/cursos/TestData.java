package org.springcloud.msvc.cursos;

import org.springcloud.msvc.cursos.models.entities.Curso;

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
}
