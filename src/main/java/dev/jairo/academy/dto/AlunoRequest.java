package dev.jairo.academy.dto;

import dev.jairo.academy.domain.Aluno;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AlunoRequest(
        @NotBlank(message = "O Nome é obrigatório")
        @Size(max = 150, message = "O campo Nome deve ter no máximo 150 caracteres.")
        String nome,

        @Past(message = "A Data de Nascimento deve estar no passado.")
        LocalDate dataNascimento,

        @Size(max = 1, message = "O campo Sexo deve ter tamanho igual a 1 caracter.")
        String sexo,

        @Size(max = 30, message = "O campo Telefone deve ter no máximo 30 caracteres.")
        String telefone,

        @Size(max = 30, message = "O campo Celular deve ter no máximo 30 caracteres.")
        String celular,

        @Email(message = "E-mail inválido.")
        @Size(max = 150, message = "O campo E-mail deve ter no máximo 150 caracteres.")
        String email,

        String observacao,

        @Size(max = 150, message = "O campo Endereço deve ter no máximo 150 caracteres.")
        String endereco,

        @Size(max = 20, message = "O campo Número deve ter no máximo 20 caracteres.")
        String numero,

        @Size(max = 100, message = "O campo Complemento deve ter no máximo 100 caracteres.")
        String complemento,

        @Size(max = 100, message = "O campo Bairro deve ter no máximo 100 caracteres.")
        String bairro,

        @Size(max = 100, message = "O campo Cidade deve ter no máximo 100 caracteres.")
        String cidade,

        @Size(max = 2, message = "O campo Estado deve ter no máximo 2 caracteres.")
        String estado,

        @Size(max = 20, message = "O campo CEP deve ter no máximo 20 caracteres.")
        String cep
) {

    public Aluno toEntity() {
        Aluno aluno = new Aluno();
        preencher(aluno);
        return aluno;
    }

    public void preencher(Aluno aluno) {
        aluno.setNome(nome);
        aluno.setDataNascimento(dataNascimento);
        aluno.setSexo(sexo);
        aluno.setTelefone(telefone);
        aluno.setCelular(celular);
        aluno.setEmail(email);
        aluno.setObservacao(observacao);
        aluno.setEndereco(endereco);
        aluno.setNumero(numero);
        aluno.setComplemento(complemento);
        aluno.setBairro(bairro);
        aluno.setCidade(cidade);
        aluno.setEstado(estado);
        aluno.setCep(cep);
    }
}
