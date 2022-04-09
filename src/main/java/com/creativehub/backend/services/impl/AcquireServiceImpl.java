package com.creativehub.backend.services.impl;



import com.creativehub.backend.models.Order;
import com.creativehub.backend.repositories.OrderRepository;
import com.creativehub.backend.services.AcquireService;
import com.creativehub.backend.services.dto.ArtworkDto;
import com.creativehub.backend.services.dto.OrderDto;
import com.creativehub.backend.services.mapper.OrderMapper;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AcquireServiceImpl implements AcquireService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final PaypalService paypalService;
    public static final String SUCCESS_URL = "/api/v1/payments/success";
    public static final String CANCEL_URL = "/api/v1/payments/cancel";
    @Value("${url.publications}")
    public String urlPublications;
    private static Order currentOrder; //implementare una struttura dati hashmap con chiave paymentid e valore l'ordine
    @Value("${url.payments}")
    public String urlPayments;

    @Override
    public String acquireArtwork(OrderDto orderDto) {

       ArtworkDto artwork = RestService(orderDto.getIdArtwork());
        if(!artwork.getOnSale()) return "redirect :/";

        currentOrder = orderMapper.orderDtoToOrder(orderDto);

        try {
            Payment payment = paypalService.createPayment(orderDto.getImporto(),artwork.getPaymentEmail(),artwork.getCurrency().getCurrencyCode(),"paypal","SALE","",
            urlPayments+CANCEL_URL,urlPayments+SUCCESS_URL);
            for(Links link:payment.getLinks()){
                if(link.getRel().equals("approval_url")){ 
                    return "redirect:"+link.getHref();  //inserire l'ordine nella struttura hashmap con chiaveil paymentid
                }
            }
        }catch (PayPalRESTException e){
            e.printStackTrace();
        }
        return "redirect :/";

    }

    @Override
    public String successAcquire(String paymentId, String payerId) {
            try{

                Payment payment = paypalService.executePayment(paymentId, payerId);
                System.out.println(payment.toJSON());

                if(payment.getState().equals("approved")){
                    orderRepository.save(currentOrder);  //salva l'ordine ed elimina dalla struttura dati hashmap la chiave paymentid
                    return "success";
                }
            } catch(PayPalRESTException e){
                System.out.println(e.getMessage());
            }
            return "redirect:/";
    }

    private ArtworkDto RestService(UUID id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<ArtworkDto> result =
                restTemplate.exchange(urlPublications+"/api/v1/publications/"+id, HttpMethod.GET, entity, ArtworkDto.class);
        return result.getBody();
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
