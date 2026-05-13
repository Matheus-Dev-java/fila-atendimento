package com.example.filaatendimento.service;

import com.example.filaatendimento.dto.SenhaResponseDTO;
import com.example.filaatendimento.model.Cliente;
import com.example.filaatendimento.model.Senha;
import com.example.filaatendimento.model.enums.TipoPrioridade;
import com.example.filaatendimento.repository.ClienteRepository;
import com.example.filaatendimento.repository.SenhaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SenhaService {

    private final SenhaRepository senhaRepository;
    private final ClienteRepository clienteRepository;

    public SenhaService(SenhaRepository senhaRepository, ClienteRepository clienteRepository) {
        this.senhaRepository = senhaRepository;
        this.clienteRepository = clienteRepository;
    }

    public SenhaResponseDTO gerarSenha(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente nao encontrado com id: " + clienteId));

        String prefixo = cliente.getTipoPrioridade() == TipoPrioridade.PREFERENCIAL ? "P" : "N";
        long total = senhaRepository.count() + 1;
        String codigo = prefixo + String.format("%03d", total);

        Senha senha = new Senha();
        senha.setCodigo(codigo);
        senha.setTipoPrioridade(cliente.getTipoPrioridade());
        senha.setCliente(cliente);

        Senha salva = senhaRepository.save(senha);
        return SenhaResponseDTO.fromEntity(salva);
    }

    public List<SenhaResponseDTO> listarNaoAtendidas() {
        return senhaRepository.findAll()
                .stream()
                .filter(s -> !s.isAtendida())
                .map(SenhaResponseDTO::fromEntity)
                .toList();
    }

    public long contarNaFila() {
        return senhaRepository.countByAtendidaFalse();
    }
}

