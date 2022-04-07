package com.creativehub.backend.services;

import com.creativehub.backend.services.dto.OrderDto;
import com.creativehub.backend.services.dto.TicketDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AcquireService {

    OrderDto acquireArtwork(UUID id);

    TicketDto acquireTicket(UUID id);

    List<OrderDto> getAllOrders(UUID id);

    List<TicketDto> getAllTickets(UUID id);

    Optional<OrderDto> findOrderById(UUID id);

    Optional<TicketDto> findTicketById(UUID id);

    void updateOrder(UUID id, OrderDto orderDto);

    void updateTicket(UUID id, TicketDto ticketDto);

    void deleteOrderById(UUID id);

    void deleteTicketById(UUID id);

}
