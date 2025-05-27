package com.galeriaCloud.galeria.repository;

import com.galeriaCloud.galeria.model.Artista;
import com.galeriaCloud.galeria.model.Obra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ObraRepository extends JpaRepository<Obra, Integer> {
    Optional<Obra> findByTitulo(String titulo);

}
