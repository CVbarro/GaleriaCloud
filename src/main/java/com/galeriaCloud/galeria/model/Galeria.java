package com.galeriaCloud.galeria.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Container (containerName = "galeria")
public class Galeria {
}
