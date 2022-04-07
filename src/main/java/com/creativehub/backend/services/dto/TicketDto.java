package com.creativehub.backend.services.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Data
public class TicketDto implements Serializable {
    private final UUID id;
    private final Float importo;
    private final Timestamp timestamp;
    private final Integer seat;
}
