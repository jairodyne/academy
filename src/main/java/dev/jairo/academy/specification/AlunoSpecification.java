package dev.jairo.academy.specification;

import dev.jairo.academy.domain.Aluno;
import dev.jairo.academy.dto.AlunoFiltroRequest;
import org.springframework.data.jpa.domain.Specification;

public class AlunoSpecification {

    public static Specification<Aluno> comFiltros(AlunoFiltroRequest filtro) {
        return Specification
                .where(nomeContem(filtro.nome()))
                .and(emailContem(filtro.email()))
                .and(celularContem(filtro.celular()))
                .and(cidadeContem(filtro.cidade()))
                .and(estadoIgual(filtro.estado()));

    }

    private static Specification<Aluno> nomeContem(String nome) {
        return (root, query, criteriaBuilder) -> {
            if(nome == null || nome.isBlank()) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
        };
    }

    private static Specification<Aluno> emailContem(String email) {
        return (root, query, criteriaBuilder) -> {
            if(email == null || email.isBlank()) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
        };
    }

    private static Specification<Aluno> celularContem(String celular) {
        return (root, query, criteriaBuilder) -> {
            if(celular == null || celular.isBlank()) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("celular")), "%" + celular.toLowerCase() + "%");
        };
    }

    private static Specification<Aluno> cidadeContem(String cidade) {
        return (root, query, criteriaBuilder) -> {
            if(cidade == null || cidade.isBlank()) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("cidade")), "%" + cidade.toLowerCase() + "%");
        };
    }

    private static Specification<Aluno> estadoIgual(String estado) {
        return (root, query, criteriaBuilder) -> {
            if(estado == null || estado.isBlank()) {
                return null;
            }
            return criteriaBuilder.equal(criteriaBuilder.upper(root.get("estado")), estado.toUpperCase());
        };
    }



}
