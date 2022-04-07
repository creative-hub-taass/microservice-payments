package com.creativehub.backend.controllers;

import com.creativehub.backend.services.AcquireService;
import com.creativehub.backend.services.DonationService;
import com.creativehub.backend.services.dto.DonationDto;
import com.creativehub.backend.services.dto.OrderDto;
import com.creativehub.backend.services.dto.TicketDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/buyartwork/{id}")
    public ResponseEntity<OrderDto> acquireArtwork(@PathVariable UUID id) {
            return ResponseEntity.ok(acquireService.acquireArtwork(id));
    }


   // Invia_donazione
    @PostMapping("/donation/{id}")
    public ResponseEntity<DonationDto> donation(@PathVariable UUID id, @RequestBody Float importo){
            return ResponseEntity.ok(donationService.saveDonation(id,importo));
    }

    // Acquista_biglietto
    @PostMapping("/buyticket/{id}")
    public ResponseEntity<TicketDto> acquireTicket(@PathVariable UUID id){
            return ResponseEntity.ok(acquireService.acquireTicket(id));
    }

    //restituisci_biglietti_comprati
    @GetMapping("/tickets/{id}")
    public List<TicketDto> getTickets(@PathVariable UUID id){
        return acquireService.getAllTickets(id);
    }

    //restituisci_donazioni
    @GetMapping("/donations/{id}")
    public List<DonationDto> getDonations(@PathVariable UUID id){
        return donationService.getAllDonations(id);
    }
}
