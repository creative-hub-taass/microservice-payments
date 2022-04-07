package com.creativehub.backend.services.impl;



import com.creativehub.backend.repositories.OrderRepository;
import com.creativehub.backend.services.AcquireService;
import com.creativehub.backend.services.dto.OrderDto;
import com.creativehub.backend.services.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AcquireServiceImpl implements AcquireService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final PaypalService paypalService;

    @Override
    public String acquireArtwork(OrderDto orderDto) {
        //TODO
        //ottengo le informazioni dell'opera tramite REST controller
        //controllo se è stata venduta (onSale == true)
        //creo il pagamento
        //creo un oggetto ordine e lo salvo nel repository
        //ritorno il link per effettuare la transazione

       // Payment payment = paypalService.createPayment(orderDto.getImporto(),artwork.getCurrency(),"paypal",)
        return null;
    }


    @Override
    public List<OrderDto> getAllOrders(UUID id) {
        return orderRepository.findAll().stream().map(orderMapper::orderToOrderDto).collect(Collectors.toList());
    }

    @Override
    public Optional<OrderDto> findOrderById(UUID id) {
        return orderRepository.findById(id).map(orderMapper::orderToOrderDto);
    }

    @Override
    public void updateOrder(UUID id, OrderDto orderDto) {
        orderRepository.findById(id).ifPresent(order -> orderMapper.updateOrderFromOrderDto(orderDto,order));
    }

    @Override
    public void deleteOrderById(UUID id) {
        orderRepository.findById(id).ifPresent(orderRepository::delete);
    }

}
