package com.creativehub.backend.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "table_order")
public class Order {
	@Id
	@GeneratedValue
	private UUID id;

	@Column(name = "id_artwork", nullable = false)
	private UUID idArtwork;

	@Column(name = "id_user", nullable = false)
	private UUID idUser;

	@Column(name = "importo", nullable = false)
	private Double importo;

	@Column(name = "destination_address", nullable = false)
	private String destinationAddress;

	@Column(name = "timestamp", nullable = false, updatable = false)
	@CreationTimestamp
	private Instant timestamp;
}
