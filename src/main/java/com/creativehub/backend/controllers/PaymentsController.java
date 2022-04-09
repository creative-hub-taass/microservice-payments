package com.creativehub.backend.controllers;

import com.creativehub.backend.services.AcquireService;
import com.creativehub.backend.services.DonationService;
import com.creativehub.backend.services.dto.DonationDto;
import com.creativehub.backend.services.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentsController {

    private final DonationService donationService;
    private final AcquireService acquireService;

   // Acquista_opera
    @PostMapping("/buyartwork")
    public String acquireArtwork(@RequestBody OrderDto orderDto) {
            return acquireService.acquireArtwork(orderDto);
    }

   // Invia_donazione
    @PostMapping("/donation")
    public String donation(@RequestBody DonationDto donationDto){
            return donationService.saveDonation(donationDto);
    }

    //pagamento fallito
    @GetMapping("/cancel")
    public String cancelPay(){
        return "cancel";
    }
    //pagamento avvenuto con successo
    @GetMapping("/success")
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
        return acquireService.successAcquire(paymentId,payerId);
    }
    //restituisci_donazioni
    @GetMapping("/donations/{id}")
    public List<DonationDto> getDonations(@PathVariable UUID id){
        return donationService.getAllDonations(id);
    }


    //restituisci_ordini
    @GetMapping("/orders/{id}")
    public List<OrderDto> getOrders(@PathVariable UUID id) { return acquireService.getAllOrders(id);}

}
