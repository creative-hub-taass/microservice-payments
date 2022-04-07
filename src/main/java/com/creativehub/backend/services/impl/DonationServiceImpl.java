package com.creativehub.backend.services.impl;

import com.creativehub.backend.models.Donation;
import com.creativehub.backend.models.Order;
import com.creativehub.backend.repositories.DonationRepository;
import com.creativehub.backend.services.DonationService;
import com.creativehub.backend.services.dto.ArtworkDto;
import com.creativehub.backend.services.dto.DonationDto;
import com.creativehub.backend.services.dto.UserDto;
import com.creativehub.backend.services.mapper.DonationMapper;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final DonationMapper donationMapper;
    private final PaypalService paypalService;
    public static final String SUCCESS_URL = "/api/v1/payments/success";
    public static final String CANCEL_URL = "/api/v1/payments/cancel";
    private static Donation currentDonation;
    @Value("${url.users}")
    public String urlUsers;
    @Value("${url.payments}")
    public String urlPayments;


    @Override
    public String saveDonation(DonationDto donationDto) {
        UserDto userCreator = RestService(donationDto.getIdCreator());

        if(userCreator.getCreator()==null) return "redirect :/";

        currentDonation = donationMapper.donationDtoToDonation(donationDto);

        try {
            Payment payment = paypalService.createPayment(donationDto.getImporto(),userCreator.getCreator().getPaymentEmail(),donationDto.getCurrency().getCurrencyCode(),"paypal","SALE","",
                    urlPayments+CANCEL_URL,urlPayments+SUCCESS_URL);
            for(Links link:payment.getLinks()){
                if(link.getRel().equals("approval_url")){
                    return "redirect:"+link.getHref();
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
                donationRepository.save(currentDonation);
                return "success";
            }
        } catch(PayPalRESTException e){
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }

    private UserDto RestService(UUID id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<UserDto> result =
                restTemplate.exchange(urlUsers+"/api/v1/auth/users/"+id, HttpMethod.GET, entity, UserDto.class);
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
        donationRepository.findById(id).ifPresent(donation -> donationMapper.updateDonationFromDonationDto(donationDto,donation));
    }

    @Override
    public void deleteDonationById(UUID id) {
        donationRepository.findById(id).ifPresent(donationRepository::delete);
    }
}
