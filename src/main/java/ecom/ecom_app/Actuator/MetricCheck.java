package ecom.ecom_app.Actuator;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;


@Service
public class MetricCheck {
    private final Counter apiCounter;

    public MetricCheck(MeterRegistry meterRegistry) {
        this.apiCounter = Counter.builder("custom.api.calls").description("Number of times custom API is called").register(meterRegistry);
    }

    public void incrementCounter() {
        apiCounter.increment();
    }
}
