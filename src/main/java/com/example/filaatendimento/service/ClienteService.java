package com.example.filaatendimento.service;

import com.example.filaatendimento.dto.ClienteRequestDTO;
import com.example.filaatendimento.dto.ClienteResponseDTO;
import com.example.filaatendimento.model.Cliente;
import com.example.filaatendimento.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteResponseDTO cadastrar(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setTipoPrioridade(dto.tipoPrioridade());
        Cliente salvo = clienteRepository.save(cliente);
        return ClienteResponseDTO.fromEntity(salvo);
    }

    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(ClienteResponseDTO::fromEntity)
                .toList();
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente nao encontrado com id: " + id));
        return ClienteResponseDTO.fromEntity(cliente);
    }
}
