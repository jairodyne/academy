package dev.jairo.academy.dto;

import dev.jairo.academy.domain.Aluno;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class AlunoResponseTest {

    @Test
    void fromEntityDeveMapearCamposDoAluno() {
        LocalDate dataNascimento = LocalDate.of(2001, 3, 15);
        LocalDateTime createdAt = LocalDateTime.of(2026, 6, 5, 10, 30);
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Joao Souza");
        aluno.setDataNascimento(dataNascimento);
        aluno.setSexo("M");
        aluno.setCelular("11988887777");
        aluno.setEmail("joao@email.com");
        aluno.setCidade("Campinas");
        aluno.setEstado("SP");
        aluno.setCreatedAt(createdAt);

        AlunoResponse response = AlunoResponse.fromEntity(aluno);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.nome()).isEqualTo("Joao Souza");
        assertThat(response.dataNascimento()).isEqualTo(dataNascimento);
        assertThat(response.sexo()).isEqualTo("M");
        assertThat(response.celular()).isEqualTo("11988887777");
        assertThat(response.email()).isEqualTo("joao@email.com");
        assertThat(response.cidade()).isEqualTo("Campinas");
        assertThat(response.estado()).isEqualTo("SP");
        assertThat(response.created_at()).isEqualTo(createdAt);
    }
}
