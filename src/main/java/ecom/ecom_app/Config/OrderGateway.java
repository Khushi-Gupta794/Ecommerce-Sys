package ecom.ecom_app.Config;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface OrderGateway {
    @Gateway(requestChannel  = "inputChannel")
    void processOrder(String order);
}
