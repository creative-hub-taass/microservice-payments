package com.creativehub.backend.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoField;
import java.util.*;


@Data
public class ArtworkDto implements Serializable, PublicationDto {

	private UUID id;
	private Instant timestamp;
	private Instant lastUpdate;
	private List<ArtworkCreationDto> creations;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private OffsetDateTime creationDateTime;
	private String name;
	private String description;
	private String type;
	private String size;
	private Integer copies;
	private Map<String, String> attributes;
	private Set<String> images;
	private Boolean onSale;
	private Double price;
	private Currency currency;
	private String paymentEmail;
	private Integer availableCopies;


	@Override
	public long getTime() {
		return creationDateTime.getLong(ChronoField.INSTANT_SECONDS);
	}


}
