package com.example.filaatendimento.dto;

import com.example.filaatendimento.model.enums.TipoPrioridade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteRequestDTO(
        @NotBlank(message = "O nome e obrigatorio")
        String nome,

        @NotNull(message = "O tipo de prioridade e obrigatorio")
        TipoPrioridade tipoPrioridade
) {}

