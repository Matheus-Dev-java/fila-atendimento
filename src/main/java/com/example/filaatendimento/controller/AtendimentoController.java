package com.example.filaatendimento.controller;

import com.example.filaatendimento.dto.AtendimentoResponseDTO;
import com.example.filaatendimento.service.AtendimentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atendimentos")
public class AtendimentoController {

    private final AtendimentoService atendimentoService;

    public AtendimentoController(AtendimentoService atendimentoService) {
        this.atendimentoService = atendimentoService;
    }

    @PostMapping("/chamar-proximo")
    public ResponseEntity<AtendimentoResponseDTO> chamarProximo() {
        return ResponseEntity.ok(atendimentoService.chamarProximo());
    }

    @PatchMapping("/finalizar/{id}")
    public ResponseEntity<AtendimentoResponseDTO> finalizar(@PathVariable Long id) {
        return ResponseEntity.ok(atendimentoService.finalizar(id));
    }

    @GetMapping("/finalizados")
    public ResponseEntity<List<AtendimentoResponseDTO>> listarFinalizados() {
        return ResponseEntity.ok(atendimentoService.listarFinalizados());
    }
}