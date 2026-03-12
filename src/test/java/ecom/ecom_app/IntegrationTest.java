package ecom.ecom_app;

import ecom.ecom_app.Config.OrderGateway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class IntegrationTest {
    @Autowired
    OrderGateway gateway;

    @Test
    void testFlow() {
        gateway.processOrder("mobile");
    }
}
