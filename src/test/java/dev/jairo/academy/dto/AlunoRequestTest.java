package dev.jairo.academy.dto;

import dev.jairo.academy.domain.Aluno;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class AlunoRequestTest {

    @Test
    void toEntityDeveMapearTodosOsCampos() {
        LocalDate dataNascimento = LocalDate.of(2000, 1, 10);
        AlunoRequest request = criarRequest(dataNascimento);

        Aluno aluno = request.toEntity();

        assertThat(aluno.getNome()).isEqualTo("Maria Silva");
        assertThat(aluno.getDataNascimento()).isEqualTo(dataNascimento);
        assertThat(aluno.getSexo()).isEqualTo("F");
        assertThat(aluno.getTelefone()).isEqualTo("1133334444");
        assertThat(aluno.getCelular()).isEqualTo("11999998888");
        assertThat(aluno.getEmail()).isEqualTo("maria@email.com");
        assertThat(aluno.getObservacao()).isEqualTo("Observacao");
        assertThat(aluno.getEndereco()).isEqualTo("Rua A");
        assertThat(aluno.getNumero()).isEqualTo("123");
        assertThat(aluno.getComplemento()).isEqualTo("Apto 1");
        assertThat(aluno.getBairro()).isEqualTo("Centro");
        assertThat(aluno.getCidade()).isEqualTo("Sao Paulo");
        assertThat(aluno.getEstado()).isEqualTo("SP");
        assertThat(aluno.getCep()).isEqualTo("01000-000");
    }

    @Test
    void preencherDeveAtualizarAlunoExistente() {
        Aluno aluno = new Aluno();
        aluno.setId(10L);
        aluno.setNome("Nome antigo");
        AlunoRequest request = criarRequest(LocalDate.of(1998, 5, 20));

        request.preencher(aluno);

        assertThat(aluno.getId()).isEqualTo(10L);
        assertThat(aluno.getNome()).isEqualTo("Maria Silva");
        assertThat(aluno.getEmail()).isEqualTo("maria@email.com");
        assertThat(aluno.getCidade()).isEqualTo("Sao Paulo");
    }

    private AlunoRequest criarRequest(LocalDate dataNascimento) {
        return new AlunoRequest(
                "Maria Silva",
                dataNascimento,
                "F",
                "1133334444",
                "11999998888",
                "maria@email.com",
                "Observacao",
                "Rua A",
                "123",
                "Apto 1",
                "Centro",
                "Sao Paulo",
                "SP",
                "01000-000"
        );
    }
}
