package com.creativehub.backend.services.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class OrderDto {
	private UUID id;
	private UUID idArtwork;
	private UUID idUser;
	private Double importo;
	private String destinationAddress;
	private Timestamp timestamp;
}
