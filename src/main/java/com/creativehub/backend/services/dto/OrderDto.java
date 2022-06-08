package com.creativehub.backend.services.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

@Data
public class OrderDto {
	private UUID id;
	private UUID idArtwork;
	private UUID idUser;
	private Double importo;
	private Currency currency;
	private String destinationAddress;
	private Instant timestamp;
}
