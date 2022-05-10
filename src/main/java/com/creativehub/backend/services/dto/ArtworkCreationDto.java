package com.creativehub.backend.services.dto;

import com.creativehub.backend.models.enums.CreationType;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class ArtworkCreationDto implements Serializable, CreationDto {
	private UUID id;
	private UUID user;
	private CreationType creationType;
	private UUID artworkId;
}