package com.rasmoo.raspaywfapi.controller;

import com.rasmoo.raspaywfapi.dto.PaymentDto;
import com.rasmoo.raspaywfapi.model.Payment;
import com.rasmoo.raspaywfapi.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/credit-card")
    public ResponseEntity<Mono<Payment>> create(@Valid  @RequestBody PaymentDto paymentDto) {
        return ResponseEntity.ok().body(paymentService.process(paymentDto));
    }

}
