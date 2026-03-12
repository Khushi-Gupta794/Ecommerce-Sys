package ecom.ecom_app.Config;

import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

@Component
public class OrderTranformer {

    @Transformer(inputChannel = "inputChannel", outputChannel = "outputChannel")
    public String transformValue(String order){
        return order.toUpperCase();
    }
}
