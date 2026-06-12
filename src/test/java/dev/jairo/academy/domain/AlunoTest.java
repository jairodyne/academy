package dev.jairo.academy.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AlunoTest {

    @Test
    void prePersistDeveDefinirCreatedAt() {
        Aluno aluno = new Aluno();

        aluno.prePersist();

        assertThat(aluno.getCreatedAt()).isNotNull();
        assertThat(aluno.getUpdatedAt()).isNull();
    }

    @Test
    void preUpdateDeveDefinirUpdatedAt() {
        Aluno aluno = new Aluno();

        aluno.preUpdate();

        assertThat(aluno.getUpdatedAt()).isNotNull();
        assertThat(aluno.getCreatedAt()).isNull();
    }
}
