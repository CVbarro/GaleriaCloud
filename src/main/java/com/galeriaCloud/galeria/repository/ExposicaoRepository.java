package com.galeriaCloud.galeria.repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.galeriaCloud.galeria.model.Exposicao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExposicaoRepository extends CosmosRepository<Exposicao, String> {
    List<Exposicao> findByArtistaNome(String artistaNome);
}
