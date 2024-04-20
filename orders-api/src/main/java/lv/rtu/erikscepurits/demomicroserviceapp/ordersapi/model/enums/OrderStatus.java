package lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    PENDING("Pending"),
    PAID("Paid");

    private final String id;
}
