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

    //
    @GetMapping("/orders/{id}")
    public List<OrderDto> getOrders(@PathVariable UUID id) { return acquireService.getAllOrders(id);}

   // Invia_donazione
    @PostMapping("/donation")
    public String donation(@RequestBody DonationDto donationDto){
            return donationService.saveDonation(donationDto);
    }


    //restituisci_donazioni
    @GetMapping("/donations/{id}")
    public List<DonationDto> getDonations(@PathVariable UUID id){
        return donationService.getAllDonations(id);
    }
}
