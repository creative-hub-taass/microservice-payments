package com.creativehub.backend.services.dto;


import com.creativehub.backend.models.enums.CreationType;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class ArtworkCreationDto implements Serializable {
    private final UUID id;
    private final UUID user;
    private final CreationType creationType;
    private final UUID artworkId;
}