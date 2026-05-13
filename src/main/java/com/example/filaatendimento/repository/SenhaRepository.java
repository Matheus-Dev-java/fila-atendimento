package com.example.filaatendimento.repository;

import com.example.filaatendimento.model.Senha;
import com.example.filaatendimento.model.enums.TipoPrioridade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SenhaRepository extends JpaRepository<Senha, Long> {

    Optional<Senha> findFirstByAtendidaFalseAndTipoPrioridadeOrderByGeradaEmAsc(TipoPrioridade tipoPrioridade);

    Optional<Senha> findFirstByAtendidaFalseOrderByGeradaEmAsc();

    long countByAtendidaFalse();
}
