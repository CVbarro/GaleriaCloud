package com.galeriaCloud.galeria.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Container(containerName = "exposicoes")
public class Exposicao {

    @Id
    private String id;

    @PartitionKey
    private String artistaNome;

    private String nome;
    private String descricao;
    private LocalDateTime data;

    private List<ItemObra> obras;
}
