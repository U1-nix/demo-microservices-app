package lv.rtu.erikscepurits.demomicroserviceapp.paymentserviceapi.bean.service;

import lombok.extern.log4j.Log4j2;
import lv.rtu.erikscepurits.demomicroserviceapp.paymentserviceapi.dto.PaymentRequest;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class PaymentService {

    public String settlePayment(PaymentRequest paymentRequest) {
        if (paymentRequest.getPrice() > 0) {
            log.info(String.format("Performed successful payment. Order id: %s, price: %f", paymentRequest.getOrderId(), paymentRequest.getPrice()));
            return "1";
        } else {
            log.error(String.format("Unable to perform payment. Order id: %s, price: %f", paymentRequest.getOrderId(), paymentRequest.getPrice()));
            return "0";
        }
    }
}
