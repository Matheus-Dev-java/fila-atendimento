package com.example.filaatendimento.dto;

import com.example.filaatendimento.model.Cliente;
import com.example.filaatendimento.model.enums.TipoPrioridade;

import java.time.LocalDateTime;

public record ClienteResponseDTO(
        Long id,
        String nome,
        TipoPrioridade tipoPrioridade,
        LocalDateTime dataCadastro
) {
    public static ClienteResponseDTO fromEntity(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTipoPrioridade(),
                cliente.getDataCadastro()
        );
    }
}
