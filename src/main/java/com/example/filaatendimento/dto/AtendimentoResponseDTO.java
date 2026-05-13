package com.example.filaatendimento.dto;

import com.example.filaatendimento.model.Atendimento;

import java.time.LocalDateTime;

public record AtendimentoResponseDTO(
        Long id,
        String codigoSenha,
        String nomeCliente,
        LocalDateTime iniciadoEm,
        LocalDateTime finalizadoEm,
        Long duracaoSegundos
) {
    public static AtendimentoResponseDTO fromEntity(Atendimento atendimento) {
        return new AtendimentoResponseDTO(
                atendimento.getId(),
                atendimento.getSenha().getCodigo(),
                atendimento.getSenha().getCliente().getNome(),
                atendimento.getIniciadoEm(),
                atendimento.getFinalizadoEm(),
                atendimento.getDuracaoSegundos()
        );
    }
}