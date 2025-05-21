package com.galeriaCloud.galeria.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Obra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String titulo;

    private String descricao;

    private LocalDateTime dtCriacao;

    private String imagemURL;

    @ManyToOne
    @JoinColumn(name = "Id_artista")
    private Artista artista;
}
