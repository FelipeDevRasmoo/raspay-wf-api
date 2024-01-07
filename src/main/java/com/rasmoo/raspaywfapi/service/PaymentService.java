package com.rasmoo.raspaywfapi.service;

import com.rasmoo.raspaywfapi.dto.PaymentDto;
import com.rasmoo.raspaywfapi.model.Payment;
import reactor.core.publisher.Mono;

public interface PaymentService {

    Mono<Payment> process(PaymentDto dto);

}
