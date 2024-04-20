package lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentRequest {
    private Long orderId;
    private Double price;
}
