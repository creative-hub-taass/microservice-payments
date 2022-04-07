package com.creativehub.backend.services.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class DonationDto {
    //private final UUID id;
    private final UUID idSender;
    private final UUID idCreator;
    private final Float importo;
    private final String message;
    private final Timestamp timestamp;
}
