package com.creativehub.backend.services.dto;

import com.creativehub.backend.models.enums.CreatorType;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

@Data
public class CreatorDto implements Serializable {
	private UUID id;
	private String name;
	private String surname;
	private Date birthDate;
	private String bio;
	private CreatorType creatorType;
	private String avatarUrl;
	private String paymentEmail;
}

