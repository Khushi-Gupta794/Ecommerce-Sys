package ecom.ecom_app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.TimeZone;

@SpringBootTest
@ActiveProfiles("test")
class EcomAppApplicationTests {


	@Test
	void contextLoads() {
       // TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

}
