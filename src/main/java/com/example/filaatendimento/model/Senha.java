package com.example.filaatendimento.model;

import com.example.filaatendimento.model.enums.TipoPrioridade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "senhas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Senha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPrioridade tipoPrioridade;

    @OneToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private boolean atendida;

    @Column(nullable = false)
    private LocalDateTime geradaEm;

    @PrePersist
    public void prePersist() {
        this.geradaEm = LocalDateTime.now();
        this.atendida = false;
    }
}
