package com.galeriaCloud.galeria.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Obra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titulo;

    private String descricao;

    private LocalDateTime dtCriacao;

    private String imagemURL;

    @Column(name = "nome_Artista")
    private String artistaNome;
}
