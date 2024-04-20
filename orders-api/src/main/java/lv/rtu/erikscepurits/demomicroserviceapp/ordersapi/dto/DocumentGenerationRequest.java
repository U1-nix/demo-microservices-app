package lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DocumentGenerationRequest implements Serializable {
    private Long id;
    private String title;
    private Double price;
    private Integer quantity;
    private String status;
}
