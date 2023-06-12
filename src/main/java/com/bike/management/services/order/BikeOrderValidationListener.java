package com.bike.management.services.order;

import com.bike.common.events.ValidateOrderRequest;
import com.bike.common.events.ValidateOrderResponse;
import com.bike.management.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BikeOrderValidationListener {

    private final BikeOrderValidator validator;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listen(ValidateOrderRequest validateOrderRequest){
        Boolean isValid = validator.validateOrder(validateOrderRequest.getBikeOrderDto());

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE,
                ValidateOrderResponse.builder()
                        .isValid(isValid)
                        .orderId(validateOrderRequest.getBikeOrderDto().getId())
                        .build());
    }
}
