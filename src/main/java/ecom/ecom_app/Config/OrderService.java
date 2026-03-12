package ecom.ecom_app.Config;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class OrderService {
    @ServiceActivator(inputChannel = "outputChannel")
    public void process(String order) {
    System.out.println("Processed Order: " + order);
    }
}
