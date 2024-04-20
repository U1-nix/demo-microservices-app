package lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.bean.client;

import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.dto.PaymentRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PaymentServiceClient {

    private String PAYMENT_SERVICE_BASE_URL = "http://localhost:9123";
    private final WebClient webClient;

    public PaymentServiceClient() {

        this.webClient = WebClient.create(PAYMENT_SERVICE_BASE_URL);
    }

    public String settlePayment(Long orderId, Double price) {
        return webClient.post()
                .uri("/settle")
                .body(BodyInserters.fromValue(new PaymentRequest(orderId, price)))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
