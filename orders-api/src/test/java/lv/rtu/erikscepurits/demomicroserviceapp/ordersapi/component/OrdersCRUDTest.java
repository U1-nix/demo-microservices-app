package lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.bean.client.ActiveMQClient;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.bean.client.PaymentServiceClient;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.bean.repository.OrderRepository;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.bean.service.DocumentService;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.bean.service.OrderService;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.bean.service.PaymentService;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.dto.OrderDto;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.model.CustomerOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrdersCRUDTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentServiceClient paymentServiceClient;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ActiveMQClient activeMQClient;

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private DocumentService documentService;

    private final OrderDto orderDto = new OrderDto();
    private final CustomerOrder order = new CustomerOrder();
    private Long id = 0L;

    @BeforeEach
    void setUp() {
        Mockito.when(orderRepository.save(Mockito.any(CustomerOrder.class))).thenAnswer(invocation -> {
            CustomerOrder savedOrder = invocation.getArgument(0);
            if (savedOrder.getId() == null) {
                savedOrder.setId(++id);
            }
            return savedOrder;
        });
    }

    @Test
    void testCreateSuccessfulOrderCreation() throws Exception {
        // Arrange
        orderDto.setTitle("Laptop");
        orderDto.setQuantity(3);
        orderDto.setPrice(100.0);

        order.setId(1L);
        order.setTitle("Laptop");
        order.setQuantity(3);
        order.setPrice(100.0);

        Mockito.when(activeMQClient.sendMessage(Mockito.anyString(), Mockito.any())).thenReturn(true);
        Mockito.when(paymentServiceClient.settlePayment(Mockito.anyLong(), Mockito.anyDouble())).thenReturn("1");

        // Act & Assert
        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(order.getId().intValue())));
    }

    @Test
    void testCreateUnsuccessfulOrderCreation() throws Exception {
        // Arrange
        orderDto.setTitle("Laptop");
        orderDto.setQuantity(3);
        orderDto.setPrice(100.0);

        order.setId(1L);
        order.setTitle("Laptop");
        order.setQuantity(3);
        order.setPrice(100.0);


        Mockito.when(activeMQClient.sendMessage(Mockito.anyString(), Mockito.any())).thenReturn(false);
        Mockito.when(paymentServiceClient.settlePayment(Mockito.anyLong(), Mockito.anyDouble())).thenReturn("0");

        // Act & Assert
        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(-1)));
    }

//    @Test
//    void testCreateSuccessfulOrderCreationAndRetrieval() throws Exception {
//        orderDto.setTitle("Laptop");
//        orderDto.setQuantity(3);
//        orderDto.setPrice(100.0);
//
//        order.setId(id);
//        order.setTitle("Laptop");
//        order.setQuantity(3);
//        order.setPrice(100.0);
//
//        Mockito.when(paymentServiceClient.settlePayment(Mockito.anyLong(), Mockito.anyDouble())).thenReturn("1");
//        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(order));
//
//        // Act & Assert
//        mockMvc.perform(post("/order")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(orderDto)))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(get("/order/{orderId}", 1))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(order.getId().intValue()))
//                .andExpect(jsonPath("$.price").value(order.getPrice()))
//                .andExpect(jsonPath("$.quantity").value(order.getQuantity()))
//                .andExpect(jsonPath("$.status").value(order.getStatus()));
//    }
}
