package com.rasmoo.raspaywfapi.service.impl;

import com.rasmoo.raspaywfapi.dto.CreditCardDto;
import com.rasmoo.raspaywfapi.exception.NotFoundException;
import com.rasmoo.raspaywfapi.mapper.CreditCardMapper;
import com.rasmoo.raspaywfapi.model.CreditCard;
import com.rasmoo.raspaywfapi.model.Customer;
import com.rasmoo.raspaywfapi.repository.CreditCardRepository;
import com.rasmoo.raspaywfapi.service.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;

    private final CreditCardMapper mapper;

    @Override
    public Mono<CreditCard> findByNumber(String number) {
        return creditCardRepository.findByNumber(number)
                .switchIfEmpty(Mono.error(()->new NotFoundException("CreditCard not found")));
    }

    @Override
    public Mono<CreditCard> create(CreditCardDto creditCardDto, Customer customer) {
        CreditCard creditCard = mapper.toModel(creditCardDto);
        creditCard.setCustomer(customer);
        return creditCardRepository.save(creditCard);
    }
}
