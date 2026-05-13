package com.example.filaatendimento.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "atendimentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "senha_id", nullable = false)
    private Senha senha;

    @Column(nullable = false)
    private LocalDateTime iniciadoEm;

    @Column
    private LocalDateTime finalizadoEm;

    @Column
    private Long duracaoSegundos;

    @PrePersist
    public void prePersist() {
        this.iniciadoEm = LocalDateTime.now();
    }
}

