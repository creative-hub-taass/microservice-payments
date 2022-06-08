package com.creativehub.backend.services.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

@Data
public class DonationDto {
	private UUID id;
	private UUID idSender;
	private UUID idCreator;
	private Double importo;
	private String message;
	private Currency currency;
	private Instant timestamp;
}
