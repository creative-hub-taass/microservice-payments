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
@Table(name = "donation")
public class Donation {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "id_sender", nullable = false)
    private UUID idSender;

    @Column(name = "id_creator", nullable = false)
    private UUID idCreator;

    @Column(name = "importo", nullable = false)
    private Float importo;

    @Column(name = "message")
    private String message;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;
}
