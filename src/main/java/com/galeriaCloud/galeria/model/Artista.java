package com.galeriaCloud.galeria.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column
    private String nome;

    @Column
    private String biografia;

    @Column
    private LocalDateTime dtNascimento;

    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL)
    private List<Obra> obras;


}
