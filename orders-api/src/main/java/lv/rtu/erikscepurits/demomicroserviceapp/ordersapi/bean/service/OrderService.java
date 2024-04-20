package lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.bean.service;

import lombok.extern.slf4j.Slf4j;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.bean.repository.OrderRepository;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.dto.OrderDto;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.model.CustomerOrder;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.model.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class OrderService {
    private final OrderMappingService orderMappingService;
    private final OrderRepository orderRepository;

    public OrderService(@Autowired  OrderMappingService orderMappingService, @Autowired OrderRepository orderRepository) {
        this.orderMappingService = orderMappingService;
        this.orderRepository = orderRepository;
    }

    public CustomerOrder createOrder(OrderDto orderDto) {
        CustomerOrder order = orderMappingService.mapDtoToEntity(orderDto);
        order.setStatus(OrderStatus.PENDING.getId());
        saveOrder(order);

        log.info(String.format("CustomerOrder created with id: %s", order.getId()));

        return order;
    }

    public OrderDto prepareOrderDto(CustomerOrder order) {
        return orderMappingService.mapEntityToDto(order);
    }
    public void updateOrderStatus(CustomerOrder order, OrderStatus status) {
        Optional<CustomerOrder> orderById = orderRepository.findById(order.getId());

        if(orderById.isPresent()) {
            CustomerOrder fetchedOrder = orderById.get();
            fetchedOrder.setStatus(status.getId());
            orderRepository.save(fetchedOrder);

            order.setStatus(status.getId());
        }
    }

    public CustomerOrder getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public void saveOrder(CustomerOrder order) {
        orderRepository.save(order);
    }
}
