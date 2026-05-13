package com.example.filaatendimento.service;

import com.example.filaatendimento.dto.AtendimentoResponseDTO;
import com.example.filaatendimento.model.Atendimento;
import com.example.filaatendimento.model.Senha;
import com.example.filaatendimento.model.enums.TipoPrioridade;
import com.example.filaatendimento.repository.AtendimentoRepository;
import com.example.filaatendimento.repository.SenhaRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AtendimentoService {

    private final AtendimentoRepository atendimentoRepository;
    private final SenhaRepository senhaRepository;

    public AtendimentoService(AtendimentoRepository atendimentoRepository, SenhaRepository senhaRepository) {
        this.atendimentoRepository = atendimentoRepository;
        this.senhaRepository = senhaRepository;
    }

    public AtendimentoResponseDTO chamarProximo() {
        Senha proxima = senhaRepository
                .findFirstByAtendidaFalseAndTipoPrioridadeOrderByGeradaEmAsc(TipoPrioridade.PREFERENCIAL)
                .orElseGet(() -> senhaRepository
                        .findFirstByAtendidaFalseOrderByGeradaEmAsc()
                        .orElseThrow(() -> new RuntimeException("Nao ha clientes na fila")));

        proxima.setAtendida(true);
        senhaRepository.save(proxima);

        Atendimento atendimento = new Atendimento();
        atendimento.setSenha(proxima);

        Atendimento salvo = atendimentoRepository.save(atendimento);
        return AtendimentoResponseDTO.fromEntity(salvo);
    }

    public AtendimentoResponseDTO finalizar(Long atendimentoId) {
        Atendimento atendimento = atendimentoRepository.findById(atendimentoId)
                .orElseThrow(() -> new RuntimeException("Atendimento nao encontrado com id: " + atendimentoId));

        if (atendimento.getFinalizadoEm() != null) {
            throw new RuntimeException("Atendimento ja foi finalizado");
        }

        LocalDateTime agora = LocalDateTime.now();
        atendimento.setFinalizadoEm(agora);
        atendimento.setDuracaoSegundos(Duration.between(atendimento.getIniciadoEm(), agora).getSeconds());

        Atendimento salvo = atendimentoRepository.save(atendimento);
        return AtendimentoResponseDTO.fromEntity(salvo);
    }

    public List<AtendimentoResponseDTO> listarFinalizados() {
        return atendimentoRepository.findByFinalizadoEmIsNotNullOrderByFinalizadoEmDesc()
                .stream()
                .map(AtendimentoResponseDTO::fromEntity)
                .toList();
    }
}
