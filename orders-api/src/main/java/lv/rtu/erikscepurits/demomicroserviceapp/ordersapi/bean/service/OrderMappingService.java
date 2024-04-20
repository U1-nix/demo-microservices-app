package lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.bean.service;

import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.dto.OrderDto;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.model.CustomerOrder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderMappingService {

    CustomerOrder mapDtoToEntity(OrderDto orderDto) {
        CustomerOrder order = new CustomerOrder();

        Optional<Long> id = Optional.ofNullable(orderDto.getId());
        Optional<String> status = Optional.ofNullable(orderDto.getStatus());

        id.ifPresent(order::setId);
        status.ifPresent(order::setStatus);

        order.setTitle(orderDto.getTitle());
        order.setQuantity(orderDto.getQuantity());
        order.setPrice(orderDto.getPrice());

        return order;
    }

    public OrderDto mapEntityToDto(CustomerOrder order) {
        OrderDto orderDto = new OrderDto();

        orderDto.setId(order.getId());
        orderDto.setStatus(order.getStatus());
        orderDto.setTitle(order.getTitle());
        orderDto.setPrice(order.getPrice());
        orderDto.setQuantity(order.getQuantity());

        return orderDto;
    }
}
