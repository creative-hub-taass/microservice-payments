package com.creativehub.backend.services.mapper;


import com.creativehub.backend.models.Donation;
import com.creativehub.backend.services.dto.DonationDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DonationMapper {
    Donation donationDtoToDonation(DonationDto donationDto);

    DonationDto donationToDonationDto(Donation donation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDonationFromDonationDto(DonationDto donationDto, @MappingTarget Donation donation);
}
