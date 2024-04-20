package lv.rtu.erikscepurits.demomicroserviceapp.ordersapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.bean")
public class OrdersApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersApiApplication.class, args);
    }

}
