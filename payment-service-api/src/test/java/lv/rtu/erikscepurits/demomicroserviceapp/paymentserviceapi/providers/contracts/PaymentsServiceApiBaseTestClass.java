package lv.rtu.erikscepurits.demomicroserviceapp.paymentserviceapi.providers.contracts;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class PaymentsServiceApiBaseTestClass {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @org.junit.jupiter.api.BeforeEach
    public void setup() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }
}
