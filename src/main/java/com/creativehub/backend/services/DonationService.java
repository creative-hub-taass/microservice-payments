package com.creativehub.backend.services;

import com.creativehub.backend.services.dto.DonationDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DonationService {

    String saveDonation(DonationDto donationDto);

    String successAcquire(String paymentId, String payerId);

    List<DonationDto> getAllDonations(UUID id);

    Optional<DonationDto> findDonationById(UUID id);

    void updateDonation(UUID id, DonationDto donationDto);

    void deleteDonationById(UUID id);

}
