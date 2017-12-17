package si.fri.rso.rlamp.lairs.api.v1.health;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import si.fri.rso.rlamp.lairbnb.lairs.services.config.LairConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Health
@ApplicationScoped
public class LairServiceHealthCheck implements HealthCheck {
    @Inject
    private LairConfig lairConfig;

    @Override
    public HealthCheckResponse call() {
        if (lairConfig.isHealthy()) {
            return HealthCheckResponse.named(LairServiceHealthCheck.class.getSimpleName()).up().build();
        }
        return HealthCheckResponse.named(LairServiceHealthCheck.class.getSimpleName()).down().build();
    };
}
