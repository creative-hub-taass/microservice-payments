package com.creativehub.backend.services.impl;

import com.creativehub.backend.repositories.DonationRepository;
import com.creativehub.backend.services.DonationService;
import com.creativehub.backend.services.dto.DonationDto;
import com.creativehub.backend.services.mapper.DonationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final DonationMapper donationMapper;


    @Override
    public DonationDto saveDonation(UUID id, Float importo) {
        //TODO
        return null;
    }

    @Override
    public List<DonationDto> getAllDonations(UUID id) {
        return donationRepository.findAll().stream().map(donationMapper::donationToDonationDto).collect(Collectors.toList());
    }

    @Override
    public Optional<DonationDto> findDonationById(UUID id) {
        return donationRepository.findById(id).map(donationMapper::donationToDonationDto);
    }

    @Override
    public void updateDonation(UUID id, DonationDto donationDto) {
        donationRepository.findById(id).ifPresent(donation -> donationMapper.updateDonationFromDonationDto(donationDto,donation));
    }

    @Override
    public void deleteDonationById(UUID id) {
        donationRepository.findById(id).ifPresent(donationRepository::delete);
    }
}
