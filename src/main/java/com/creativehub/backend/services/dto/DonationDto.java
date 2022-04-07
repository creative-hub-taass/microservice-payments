package com.creativehub.backend.services.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Currency;
import java.util.UUID;

@Data
public class DonationDto {
    private final UUID id;
    private final UUID idSender;
    private final UUID idCreator;
    private final Double importo;
    private final String message;
    private final Currency currency;
    private final Timestamp timestamp;
}
