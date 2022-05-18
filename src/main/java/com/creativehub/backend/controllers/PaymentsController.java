package com.creativehub.backend.controllers;

import com.creativehub.backend.services.AcquireService;
import com.creativehub.backend.services.DonationService;
import com.creativehub.backend.services.dto.DonationDto;
import com.creativehub.backend.services.dto.OrderDto;
import com.creativehub.backend.util.Utils;
import io.micrometer.core.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
	@Value("${client.url}")
	private String clientUrl;

	// Acquista_opera
	@PostMapping("/buyartwork")
	public String acquireArtwork(@RequestBody OrderDto orderDto) {
		return acquireService.acquireArtwork(orderDto);
	}

	// Invia_donazione
	@PostMapping("/donation")
	public String donation(@RequestBody DonationDto donationDto) {
		return donationService.saveDonation(donationDto);
	}

	//pagamento fallito
	@GetMapping("/-/cancel")
	public String cancelPay() {
		return Utils.buildResponseFailed(clientUrl, "Payment canceled");
	}

	//pagamento avvenuto con successo
	@GetMapping("/-/acquire/success")
	public String successPayAcquire(@RequestParam("paymentId") String paymentId, @RequestParam(value = "token", required = false) String token, @RequestParam("PayerID") String payerId) {
		return acquireService.successAcquire(paymentId, payerId);
	}

	//pagamento avvenuto con successo
	@GetMapping("/-/donation/success")
	public String successPayDonation(@RequestParam("paymentId") String paymentId, @RequestParam(value = "token", required = false) String token, @RequestParam("PayerID") String payerId) {
		return donationService.successAcquire(paymentId, payerId);
	}

	//restituisci_donazioni
	@GetMapping("/donations/{id}")
	public List<DonationDto> getDonations(@PathVariable UUID id) {
		return donationService.getAllDonations(id);
	}

	//restituisci_ordini
	@GetMapping("/orders/{id}")
	public List<OrderDto> getOrders(@PathVariable UUID id) {
		return acquireService.getAllOrders(id);
	}
}
