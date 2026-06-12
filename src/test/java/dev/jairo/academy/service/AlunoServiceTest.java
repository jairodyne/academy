package dev.jairo.academy.service;

import dev.jairo.academy.domain.Aluno;
import dev.jairo.academy.dto.AlunoFiltroRequest;
import dev.jairo.academy.dto.AlunoRequest;
import dev.jairo.academy.dto.AlunoResponse;
import dev.jairo.academy.exception.RegraNegocioException;
import dev.jairo.academy.repository.AlunoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    private AlunoService alunoService;

    @BeforeEach
    void setUp() {
        alunoService = new AlunoService(alunoRepository);
    }

    @Test
    void cadastrarDeveSalvarAlunoQuandoEmailNaoExiste() {
        AlunoRequest request = criarRequest("Ana Lima", "ana@email.com");
        when(alunoRepository.existsByEmail("ana@email.com")).thenReturn(false);
        when(alunoRepository.save(any(Aluno.class))).thenAnswer(invocation -> {
            Aluno aluno = invocation.getArgument(0);
            aluno.setId(1L);
            return aluno;
        });

        AlunoResponse response = alunoService.cadastrar(request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.nome()).isEqualTo("Ana Lima");
        assertThat(response.email()).isEqualTo("ana@email.com");

        ArgumentCaptor<Aluno> alunoCaptor = ArgumentCaptor.forClass(Aluno.class);
        verify(alunoRepository).save(alunoCaptor.capture());
        assertThat(alunoCaptor.getValue().getNome()).isEqualTo("Ana Lima");
        assertThat(alunoCaptor.getValue().getEmail()).isEqualTo("ana@email.com");
    }

    @Test
    void cadastrarDeveLancarExcecaoQuandoEmailJaExiste() {
        AlunoRequest request = criarRequest("Ana Lima", "ana@email.com");
        when(alunoRepository.existsByEmail("ana@email.com")).thenReturn(true);

        assertThatThrownBy(() -> alunoService.cadastrar(request))
                .isInstanceOf(RegraNegocioException.class)
                .hasMessage("Já existe um Aluno cadastrado com este email");

        verify(alunoRepository, never()).save(any());
    }

    @Test
    void cadastrarNaoDeveConsultarEmailQuandoEmailForNulo() {
        AlunoRequest request = criarRequest("Ana Lima", null);
        when(alunoRepository.save(any(Aluno.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AlunoResponse response = alunoService.cadastrar(request);

        assertThat(response.nome()).isEqualTo("Ana Lima");
        assertThat(response.email()).isNull();
        verify(alunoRepository, never()).existsByEmail(any());
    }

    @Test
    void listarDeveRetornarPaginaMapeada() {
        Aluno aluno = criarAluno(1L, "Carlos Santos", "carlos@email.com");
        Pageable pageable = PageRequest.of(0, 10);
        when(alunoRepository.findAll(anySpecification(), eq(pageable)))
                .thenReturn(new PageImpl<>(List.of(aluno), pageable, 1));

        Page<AlunoResponse> pagina = alunoService.listar(new AlunoFiltroRequest(null, null, null, null, null), pageable);

        assertThat(pagina.getTotalElements()).isEqualTo(1);
        assertThat(pagina.getContent().getFirst().id()).isEqualTo(1L);
        assertThat(pagina.getContent().getFirst().nome()).isEqualTo("Carlos Santos");
    }

    @Test
    void buscarPorIdDeveRetornarAlunoQuandoEncontrado() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(criarAluno(1L, "Carlos Santos", "carlos@email.com")));

        AlunoResponse response = alunoService.buscarPorId(1L);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.nome()).isEqualTo("Carlos Santos");
    }

    @Test
    void buscarPorIdDeveLancarExcecaoQuandoNaoEncontrado() {
        when(alunoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> alunoService.buscarPorId(99L))
                .isInstanceOf(RegraNegocioException.class)
                .hasMessage("Aluno não encontrado.");
    }

    @Test
    void atualizarDevePreencherAlunoExistenteESalvar() {
        Aluno aluno = criarAluno(1L, "Nome antigo", "antigo@email.com");
        AlunoRequest request = criarRequest("Nome novo", "novo@email.com");
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(alunoRepository.save(aluno)).thenReturn(aluno);

        AlunoResponse response = alunoService.atualizar(1L, request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.nome()).isEqualTo("Nome novo");
        assertThat(response.email()).isEqualTo("novo@email.com");
        verify(alunoRepository).save(aluno);
    }

    @Test
    void excluirDeveRemoverAlunoQuandoEncontrado() {
        Aluno aluno = criarAluno(1L, "Carlos Santos", "carlos@email.com");
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

        alunoService.excluir(1L);

        verify(alunoRepository).delete(aluno);
    }

    @Test
    void excluirDeveLancarExcecaoQuandoNaoEncontrado() {
        when(alunoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> alunoService.excluir(99L))
                .isInstanceOf(RegraNegocioException.class)
                .hasMessage("Aluno não encontrado.");

        verify(alunoRepository, never()).delete(any(Aluno.class));
    }

    @SuppressWarnings("unchecked")
    private Specification<Aluno> anySpecification() {
        return any(Specification.class);
    }

    private AlunoRequest criarRequest(String nome, String email) {
        return new AlunoRequest(
                nome,
                LocalDate.of(2000, 1, 1),
                "F",
                "1133334444",
                "11999998888",
                email,
                null,
                "Rua A",
                "123",
                null,
                "Centro",
                "Sao Paulo",
                "SP",
                "01000-000"
        );
    }

    private Aluno criarAluno(Long id, String nome, String email) {
        Aluno aluno = new Aluno();
        aluno.setId(id);
        aluno.setNome(nome);
        aluno.setDataNascimento(LocalDate.of(2000, 1, 1));
        aluno.setSexo("M");
        aluno.setCelular("11988887777");
        aluno.setEmail(email);
        aluno.setCidade("Sao Paulo");
        aluno.setEstado("SP");
        return aluno;
    }
}
