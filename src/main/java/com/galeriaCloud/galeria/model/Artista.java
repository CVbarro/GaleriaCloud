package com.galeriaCloud.galeria.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Artista {

    public Artista() {
        this.obras = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nome;

    @Column
    private String biografia;

    @Column
    private LocalDateTime dtNascimento;

    @OneToMany
    @JoinColumn(name = "artista_nome", referencedColumnName = "nome")
    private List<Obra> obras;
}

