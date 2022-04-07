package com.creativehub.backend.services.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class OrderDto {
    private final UUID id;
    private final UUID idArtwork;
    private final UUID idUser;
    private final Float importo;
    private final String destinationAddress;
    private final Timestamp timestamp;
}
