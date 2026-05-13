package com.example.filaatendimento.dto;

import com.example.filaatendimento.model.Senha;
import com.example.filaatendimento.model.enums.TipoPrioridade;

import java.time.LocalDateTime;

public record SenhaResponseDTO(
        Long id,
        String codigo,
        TipoPrioridade tipoPrioridade,
        String nomeCliente,
        boolean atendida,
        LocalDateTime geradaEm
) {
    public static SenhaResponseDTO fromEntity(Senha senha) {
        return new SenhaResponseDTO(
                senha.getId(),
                senha.getCodigo(),
                senha.getTipoPrioridade(),
                senha.getCliente().getNome(),
                senha.isAtendida(),
                senha.getGeradaEm()
        );
    }
}
