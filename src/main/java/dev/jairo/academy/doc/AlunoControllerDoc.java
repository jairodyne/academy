package dev.jairo.academy.doc;

import dev.jairo.academy.dto.AlunoFiltroRequest;
import dev.jairo.academy.dto.AlunoRequest;
import dev.jairo.academy.dto.AlunoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(
        name = "Alunos",
        description = "Operações para cadastro, consulta, atualização, exclusão e filtro de alunos."
)
public interface AlunoControllerDoc {

    @Operation(
            summary = "Cadastrar Aluno",
            description = "Cria um novo Aluno no sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Aluno cadastrado com sucesso."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação ou regra de negócio. ",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))

                    )
            }
    )
    AlunoResponse cadastrar
            (
                    @RequestBody
                    @Valid
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Dados necessários para cadastrar um Aluno",
                            required = true,
                            content = @Content(schema = @Schema(implementation = AlunoRequest.class),
                            examples = @ExampleObject(
                                    name = "Aluno válido",
                                    value = """
                                            {
                                                  "nome": "Maria Oliveira",
                                                  "dataNascimento": "1997-09-21",
                                                  "sexo": "F",
                                                  "telefone": "1112345644",
                                                  "celular": "119911189898",
                                                  "email": "joao@email.com",
                                                  "observacao": "Aluno iniciante",
                                                  "endereco": "Rua das Flores",
                                                  "numero": "123",
                                                  "complemento": "Apartamento 169",
                                                  "bairro": "Centro",
                                                  "cidade": "Sao Paulo",
                                                  "estado": "SP",
                                                  "cep": "03155-099"
                                            
                                            }
                                            """
                            ))
                    )

             AlunoRequest alunoRequest
            );

    @Operation(
            summary = "Listar alunos",
            description = "Lista Alunos de forma paginada, permitindo filtros adicionais por nome, e-mail, celular, cidade e estado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de Alunos retornada com sucesso.")
            }
    )
    Page<AlunoResponse> listar(
            @Parameter(description = "Filtros adicionais para busca de alunos")
            AlunoFiltroRequest filtro,

            @Parameter(description = "Informações de paginação e ordenação")
            Pageable pageable
    );

    @Operation(
            summary = "Buscar Aluno por ID",
            description = "Retorna os dados de um Aluno específico " ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Aluno encontrado com sucesso."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Aluno não encontrado",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    AlunoResponse buscarPorId(
            @Parameter(description = "ID do Aluno", example = "2", required = true)
            Long id);
}
