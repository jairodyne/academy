package dev.jairo.academy.repository;

import dev.jairo.academy.domain.Graduacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraduacaoRepository extends JpaRepository<Graduacao, Long> {
}
