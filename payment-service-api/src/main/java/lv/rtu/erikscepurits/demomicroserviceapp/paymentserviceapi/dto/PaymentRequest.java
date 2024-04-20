package lv.rtu.erikscepurits.demomicroserviceapp.paymentserviceapi.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private Integer orderId;
    private Double price;
}
