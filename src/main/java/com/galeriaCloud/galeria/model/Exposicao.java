package com.galeriaCloud.galeria.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Exposicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String artistaNome;

    private String nome;
    private String descricao;

    private LocalDateTime data;

    @ManyToMany
    @JoinTable(
            name = "exposicao_obra",
            joinColumns = @JoinColumn(name = "exposicao_id"),
            inverseJoinColumns = @JoinColumn(name = "obra_id")
    )
    private List<Obra> obras;
}
