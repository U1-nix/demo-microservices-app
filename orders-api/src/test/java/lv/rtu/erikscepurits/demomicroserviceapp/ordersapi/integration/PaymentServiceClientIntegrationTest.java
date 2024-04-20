package lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.integration;

import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.bean.client.PaymentServiceClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(PaymentServiceClient.class)
class PaymentServiceClientIntegrationTest {

    @Autowired
    private PaymentServiceClient paymentServiceClient;

    @Test
    void testSuccessfulPaymentSettlement() {
        // Arrange
        Long orderId = 100L;
        Double price = 50.0;

        // Act
        String paymentResult = paymentServiceClient.settlePayment(orderId, price);

        // Assert
        Assertions.assertEquals("1", paymentResult);
    }

    @Test
    void testUnsuccessfulPaymentSettlement() {
        // Arrange
        Long orderId = 100L;
        Double price = -2.0;

        // Act
        String paymentResult = paymentServiceClient.settlePayment(orderId, price);

        // Assert
        Assertions.assertEquals("0", paymentResult);
    }

}
