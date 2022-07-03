package com.creativehub.backend.services.impl;


import com.creativehub.backend.models.Order;
import com.creativehub.backend.repositories.OrderRepository;
import com.creativehub.backend.services.AcquireService;
import com.creativehub.backend.services.ProducerService;
import com.creativehub.backend.services.dto.ArtworkDto;
import com.creativehub.backend.services.dto.OrderDto;
import com.creativehub.backend.services.mapper.OrderMapper;
import com.creativehub.backend.util.Utils;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AcquireServiceImpl implements AcquireService {
	private static final HashMap<String, Order> order_map = new HashMap<>();
	private final OrderRepository orderRepository;
	private final OrderMapper orderMapper;
	private final PaypalService paypalService;
	private final ProducerService producerService;
	@Value("${path.success.acquire}")
	public String SUCCESS_URL;
	@Value("${path.cancel}")
	public String CANCEL_URL;
	@Value("${publications.url}")
	public String urlPublications;
	@Value("${gateway.url}")
	private String gatewayUrl;
	@Value("${client.url}")
	private String clientUrl;

	@Override
	public String acquireArtwork(OrderDto orderDto) {
		ArtworkDto artwork = fetchArtwork(orderDto.getIdArtwork());
		if (artwork == null) {
			//return Utils.buildResponseFailed(clientUrl, "Artwork not found");
			return "Artwork not found";
		} else if (!artwork.getOnSale()) {
			//return Utils.buildResponseFailed(clientUrl, "Artwork not for sale");
			return "Artwork not for sale";
		}
		try {
			Payment payment = paypalService.createPayment(orderDto.getImporto(), artwork.getPaymentEmail(), artwork.getCurrency().getCurrencyCode(), "paypal", "SALE", "",
					gatewayUrl + CANCEL_URL, gatewayUrl + SUCCESS_URL);
			for (Links link : payment.getLinks()) {
				if (link.getRel().equals("approval_url")) {
					order_map.put(payment.getId(), orderMapper.orderDtoToOrder(orderDto));
					return "redirect:" + link.getHref();
				}
			}
		} catch (PayPalRESTException e) {
			//return Utils.buildResponseFailed(clientUrl, e.getMessage());
			return e.getMessage();
		}
		//return Utils.buildResponseFailed(clientUrl, "An error was found in acquiring the artwork");
		return "An error was found in acquiring the artwork";
	}

	@Override
	public String successAcquire(String paymentId, String payerId) {
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			System.out.println(payment.toJSON());
			if (payment.getState().equals("approved")) {
				Order order = order_map.get(paymentId);
				orderRepository.save(order);
				order_map.remove(paymentId);
				updateBoughtArtwork(order.getIdArtwork());
				return Utils.buildResponseSuccessful(clientUrl);
			}
		} catch (PayPalRESTException e) {
			return Utils.buildResponseFailed(clientUrl, e.getMessage());
		}
		return Utils.buildResponseFailed(clientUrl, "An error was found during the purchase.");
	}

	private void updateBoughtArtwork(UUID idArtwork) {
		producerService.sendMessage(idArtwork);
	}

	private ArtworkDto fetchArtwork(UUID id) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI4NDg0MDMwMy0zYTRiLTRmMTctYTIyYi04Y2Q1YjVjZDczZDIiLCJleHAiOjE2NDg5OTkxODMsImlhdCI6MTY0ODk5ODU4Mywicm9sZXMiOlsiVVNFUiJdfQ.OF0leRjcb3AS4IGuuzXezTOD8U97CoqWZUK3VLp-phI");
		headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		ResponseEntity<ArtworkDto> result =
				restTemplate.exchange(urlPublications + "/api/v1/publications/-/artworks/" + id, HttpMethod.GET, entity, ArtworkDto.class);
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
		orderRepository.findById(id).ifPresent(order -> orderMapper.updateOrderFromOrderDto(orderDto, order));
	}

	@Override
	public void deleteOrderById(UUID id) {
		orderRepository.findById(id).ifPresent(orderRepository::delete);
	}
}
