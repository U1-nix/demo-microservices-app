package lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.bean.controller;

import lombok.extern.slf4j.Slf4j;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.bean.service.DocumentService;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.bean.service.OrderService;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.bean.service.PaymentService;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.dto.OrderDto;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.model.CustomerOrder;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.model.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
public class OrdersCRUD {

    private final OrderService orderService;
    private final DocumentService documentService;
    private final PaymentService paymentService;

    public OrdersCRUD(@Autowired OrderService orderService, @Autowired DocumentService documentService, @Autowired PaymentService paymentService) {
        this.orderService = orderService;
        this.documentService = documentService;
        this.paymentService = paymentService;
    }

    @PostMapping("/order")
    Long createOrder(@RequestBody OrderDto orderDto) {
        log.info("CustomerOrder was received: " + orderDto);

        CustomerOrder order = orderService.createOrder(orderDto);

        // API call
        boolean paymentResult = paymentService.performPayment(order);

        if (paymentResult) {
            // DB layer
            orderService.updateOrderStatus(order, OrderStatus.PENDING);

            // send message to queue
            documentService.requestDocumentGeneration(order);

            return order.getId();
        } else {
            return -1L;
        }
    }

    @GetMapping("/order/{orderId}")
    OrderDto getOrderById(@PathVariable String orderId) {
        log.info(String.format("Requested order with id: %s", orderId));

        CustomerOrder order = orderService.getOrder(Long.valueOf(orderId));

        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }

        return orderService.prepareOrderDto(order);
    }
}
