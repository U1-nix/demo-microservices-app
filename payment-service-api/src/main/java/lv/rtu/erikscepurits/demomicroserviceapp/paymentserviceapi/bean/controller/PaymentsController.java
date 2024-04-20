package lv.rtu.erikscepurits.demomicroserviceapp.paymentserviceapi.bean.controller;

import lombok.extern.slf4j.Slf4j;
import lv.rtu.erikscepurits.demomicroserviceapp.paymentserviceapi.bean.service.PaymentService;
import lv.rtu.erikscepurits.demomicroserviceapp.paymentserviceapi.dto.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PaymentsController {

    private final PaymentService paymentService;

    public PaymentsController(@Autowired PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/settle")
    String settlePayment(@RequestBody PaymentRequest paymentRequest) {
        log.info(String.format("Payment settlement request received. Order id: %s", paymentRequest.getOrderId()));

        return paymentService.settlePayment(paymentRequest);
    }
}
