package ecom.ecom_app.Actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HealthCheck  implements HealthIndicator {
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    private final String EXTERNAL_URL = "http://localhost:8081/api/status";

    @Override
    public Health health() {
//      try {
//            ResponseEntity<String> response = restTemplate.getForEntity(EXTERNAL_URL, String.class);
//      if (response.getStatusCode().is2xxSuccessful()) {
//      return Health.up().withDetail("External Service", "Available").withDetail("Status Code", response.getStatusCode()).build();
//            }
//      else {
//          return Health.down().withDetail("External Service", "Returned non-OK status").build();
//            }
//      } catch (Exception e) {return Health.down().withDetail("External Service", "Not reachable").withException(e).build();
//        }
//    }

        // for not using other external url, one app same url
        boolean serviceLogicWorking = checkSomething();
         if (serviceLogicWorking) {
             return Health.up().withDetail("Custom Check", "Everything is working fine").build();
            }
         else {
                return Health.down().withDetail("Custom Check", "Something is wrong").build();
            }
        }
        private boolean checkSomething() {
            return true;
        }




}
