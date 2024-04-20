package lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.bean.service;

import lombok.extern.slf4j.Slf4j;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.bean.client.PaymentServiceClient;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.model.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class PaymentService {

    private final PaymentServiceClient paymentServiceClient;

    public PaymentService(@Autowired PaymentServiceClient paymentServiceClient) {
        this.paymentServiceClient = paymentServiceClient;
    }

    public boolean performPayment(CustomerOrder order) {
        log.info(String.format("Attempting to perform payment for order id: %s", order.getId()));

        double totalAmount = order.getQuantity() * order.getPrice();

        return makePaymentRequest(order.getId(), totalAmount);
    }

    private boolean makePaymentRequest(Long orderId, double totalAmount) {
        String paymentResult = paymentServiceClient.settlePayment(orderId, totalAmount);
        log.info(String.format("Payment settlement result: %s for the order id: %s", paymentResult, orderId));

        return "1".equals(paymentResult);
    }
}
