package ecom.ecom_app.Actuator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricCountController {
    private final MetricCheck metricService;

    public MetricCountController(MetricCheck metricService) {
        this.metricService = metricService;
    }

    @GetMapping("/api/test")
    public String testApi() {
        metricService.incrementCounter();
        return "API Called!";
    }
}
