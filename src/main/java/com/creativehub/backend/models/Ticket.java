package com.creativehub.backend.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "importo", nullable = false)
    private Float importo;

    @Column(name = "seat")
    private Integer seat;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;
}
