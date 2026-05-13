package com.example.filaatendimento.repository;

import com.example.filaatendimento.model.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    List<Atendimento> findByFinalizadoEmIsNotNullOrderByFinalizadoEmDesc();
}