package ecom.ecom_app;

import ecom.ecom_app.Config.OrderGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.TimeZone;

@SpringBootApplication
@EnableCaching
@EnableFeignClients(basePackages = "ecom.ecom_app.FeignClient")
public class  EcomAppApplication implements CommandLineRunner {

    @Autowired
    private OrderGateway gateway;

	public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(EcomAppApplication.class, args);
	}
    @Override
    public void run(String... args) {
    gateway.processOrder("laptop order");
    }
}


