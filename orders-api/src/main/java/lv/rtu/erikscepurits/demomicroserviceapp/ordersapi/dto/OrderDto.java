package lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.dto;

import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private String title;
    private Double price;
    private Integer quantity;
    private String status;
}
