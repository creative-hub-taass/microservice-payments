package com.creativehub.backend.services.mapper;

import com.creativehub.backend.models.Ticket;
import com.creativehub.backend.services.dto.TicketDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface TicketMapper {
    Ticket ticketDtoToTicket(TicketDto ticketDto);

    TicketDto ticketToTicketDto(Ticket ticket);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTicketFromTicketDto(TicketDto ticketDto, @MappingTarget Ticket ticket);
}
