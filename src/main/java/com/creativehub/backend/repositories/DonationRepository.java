package com.creativehub.backend.repositories;

import com.creativehub.backend.models.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DonationRepository extends JpaRepository<Donation, UUID> {
}
