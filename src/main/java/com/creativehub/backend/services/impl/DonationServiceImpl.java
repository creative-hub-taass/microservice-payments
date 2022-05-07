package com.creativehub.backend.services.impl;

import com.creativehub.backend.models.Donation;
import com.creativehub.backend.repositories.DonationRepository;
import com.creativehub.backend.services.DonationService;
import com.creativehub.backend.services.dto.DonationDto;
import com.creativehub.backend.services.dto.UserDto;
import com.creativehub.backend.services.mapper.DonationMapper;
import com.creativehub.backend.util.buildResponse;
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
public class DonationServiceImpl implements DonationService {
	private static final HashMap<String, Donation> donation_map = new HashMap<>();
	@Value("${path.success}")
	public String SUCCESS_URL;
	@Value("${path.cancel}")
	public String CANCEL_URL;
	private final DonationRepository donationRepository;
	private final DonationMapper donationMapper;
	private final PaypalService paypalService;
	@Value("${users.url}")
	public String urlUsers;
	@Value("${gateway.url}")
	private String gatewayUrl;


	@Override
	public String saveDonation(DonationDto donationDto) {
		UserDto userCreator = RestService(donationDto.getIdCreator());
		if (userCreator == null || userCreator.getCreator() == null) {
			buildResponse rsp = new buildResponse("failed", "Didn't find creator");
			return rsp.getHTML();
		}
		try {
			Payment payment = paypalService.createPayment(donationDto.getImporto(), userCreator.getCreator().getPaymentEmail(), donationDto.getCurrency().getCurrencyCode(), "paypal", "SALE", "",
					gatewayUrl + CANCEL_URL, gatewayUrl + SUCCESS_URL);
			for (Links link : payment.getLinks()) {
				if (link.getRel().equals("approval_url")) {
					donation_map.put(payment.getId(), donationMapper.donationDtoToDonation(donationDto));
					return "redirect:" + link.getHref();
				}
			}
		} catch (PayPalRESTException e) {
			buildResponse rsp = new buildResponse("failed", e.getMessage());
			return rsp.getHTML();
		}
		buildResponse rsp = new buildResponse("failed", "An error was found in acquiring the creator");
		return rsp.getHTML();
	}

	@Override
	public String successAcquire(String paymentId, String payerId) {
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			System.out.println(payment.toJSON());
			if (payment.getState().equals("approved")) {
				donationRepository.save(donation_map.get(paymentId));
				donation_map.remove(paymentId);
				buildResponse rsp = new buildResponse("was successful", "");
				return rsp.getHTML();
			}
		} catch (PayPalRESTException e) {
			buildResponse rsp = new buildResponse("failed", e.getMessage());
			return rsp.getHTML();
		}
		buildResponse rsp = new buildResponse("failed", "An error was found during the purchase.");
		return rsp.getHTML();
	}

	private UserDto RestService(UUID id) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		ResponseEntity<UserDto> result = restTemplate.exchange(urlUsers + "/api/v1/users/" + id, HttpMethod.GET, entity, UserDto.class);
		return result.getBody();
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
		donationRepository.findById(id).ifPresent(donation -> donationMapper.updateDonationFromDonationDto(donationDto, donation));
	}

	@Override
	public void deleteDonationById(UUID id) {
		donationRepository.findById(id).ifPresent(donationRepository::delete);
	}
}
