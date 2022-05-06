package com.creativehub.backend.services.dto;

import com.creativehub.backend.models.enums.CreatorType;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class CreatorDto implements Serializable {
	private Long id;
	private String name;
	private String surname;
	private Date birthDate;
	private String bio;
	private CreatorType creatorType;
	private String avatarUrl;
	private String paymentEmail;

}

