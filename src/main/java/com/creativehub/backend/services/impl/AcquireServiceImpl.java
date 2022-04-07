package com.creativehub.backend.services.impl;



import com.creativehub.backend.repositories.OrderRepository;
import com.creativehub.backend.repositories.TicketRepository;
import com.creativehub.backend.services.AcquireService;
import com.creativehub.backend.services.dto.OrderDto;
import com.creativehub.backend.services.dto.TicketDto;
import com.creativehub.backend.services.mapper.OrderMapper;
import com.creativehub.backend.services.mapper.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AcquireServiceImpl implements AcquireService {

    private final TicketRepository ticketRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final TicketMapper ticketMapper;

    @Override
    public OrderDto acquireArtwork(UUID id) {
        //TODO
        return null;
    }

    @Override
    public TicketDto acquireTicket(UUID id) {
        //TODO
        return null;
    }

    @Override
    public List<OrderDto> getAllOrders(UUID id) {
        return orderRepository.findAll().stream().map(orderMapper::orderToOrderDto).collect(Collectors.toList());
    }

    @Override
    public List<TicketDto> getAllTickets(UUID id) {
        return ticketRepository.findAll().stream().map(ticketMapper::ticketToTicketDto).collect(Collectors.toList());
    }

    @Override
    public Optional<OrderDto> findOrderById(UUID id) {
        return orderRepository.findById(id).map(orderMapper::orderToOrderDto);
    }

    @Override
    public Optional<TicketDto> findTicketById(UUID id) {
        return ticketRepository.findById(id).map(ticketMapper::ticketToTicketDto);
    }

    @Override
    public void updateOrder(UUID id, OrderDto orderDto) {
        orderRepository.findById(id).ifPresent(order -> orderMapper.updateOrderFromOrderDto(orderDto,order));
    }

    @Override
    public void updateTicket(UUID id, TicketDto ticketDto) {
        ticketRepository.findById(id).ifPresent(ticket -> ticketMapper.updateTicketFromTicketDto(ticketDto,ticket));
    }

    @Override
    public void deleteOrderById(UUID id) {
        orderRepository.findById(id).ifPresent(orderRepository::delete);
    }

    @Override
    public void deleteTicketById(UUID id) {
        ticketRepository.findById(id).ifPresent(ticketRepository::delete);
    }
}
