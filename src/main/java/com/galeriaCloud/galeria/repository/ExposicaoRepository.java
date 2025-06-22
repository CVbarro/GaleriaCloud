package com.galeriaCloud.galeria.repository;

import com.galeriaCloud.galeria.model.Exposicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExposicaoRepository extends JpaRepository<Exposicao, String> {
    List<Exposicao> findByArtistaNome(String artistaNome);
}
