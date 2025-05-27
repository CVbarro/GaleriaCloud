package com.galeriaCloud.galeria.repository;

import com.galeriaCloud.galeria.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista, Integer> {
    Optional<Artista> findByNome(String artistaNome);
}
