package si.fri.rso.rlamp.lairbnb.lairs.services.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("service-properties")
public class LairConfig {
    @ConfigValue(value = "external-services.user-service.enabled", watch = true)
    private boolean userServiceEnabled;

    @ConfigValue(value = "external-services.reservation-service.enabled", watch = true)
    private boolean reservationServiceEnabled;

    @ConfigValue(value = "service.healthy", watch = true)
    private boolean healthy;

    public boolean isUserServiceEnabled() {
        return userServiceEnabled;
    }

    public void setUserServiceEnabled(boolean userServiceEnabled) {
        this.userServiceEnabled = userServiceEnabled;
    }

    public boolean isReservationServiceEnabled() {
        return reservationServiceEnabled;
    }

    public void setReservationServiceEnabled(boolean reservationServiceEnabled) {
        this.reservationServiceEnabled = reservationServiceEnabled;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }
}
