package com.example.filaatendimento.controller;

import com.example.filaatendimento.dto.SenhaResponseDTO;
import com.example.filaatendimento.service.SenhaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/senhas")
public class SenhaController {

    private final SenhaService senhaService;

    public SenhaController(SenhaService senhaService) {
        this.senhaService = senhaService;
    }

    @PostMapping("/gerar/{clienteId}")
    public ResponseEntity<SenhaResponseDTO> gerarSenha(@PathVariable Long clienteId) {
        SenhaResponseDTO response = senhaService.gerarSenha(clienteId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/fila")
    public ResponseEntity<List<SenhaResponseDTO>> listarNaoAtendidas() {
        return ResponseEntity.ok(senhaService.listarNaoAtendidas());
    }

    @GetMapping("/fila/total")
    public ResponseEntity<Map<String, Long>> contarNaFila() {
        long total = senhaService.contarNaFila();
        return ResponseEntity.ok(Map.of("totalNaFila", total));
    }
}

