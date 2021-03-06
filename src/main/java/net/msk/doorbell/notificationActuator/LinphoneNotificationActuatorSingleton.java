package net.msk.doorbell.notificationActuator;

import net.msk.doorbell.DoorbellEvent;
import net.msk.doorbell.service.DoorbellService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.concurrent.TimeUnit;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Profile("!dev")
public class LinphoneNotificationActuatorSingleton implements NotificationActuator {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinphoneNotificationActuatorSingleton.class);

    @Value("${notificationActuator.voip.call.number}")
    private String VOIP_NUMBER_TO_CALL;

    @Value("${notificationActuator.voip.call.timeout_seconds:8}")
    private int VOIP_CALL_TIMEOUT_SECONDS;

    LinphoneNotificationActuatorSingleton(final DoorbellService doorbellService) {
        LOGGER.trace("Created bean 'LinphoneControllerSingleton'.");
        doorbellService.registerNotificationActuator(this);
    }

    @Override
    public NotificationActuatorType getType() {
        return NotificationActuatorType.voip;
    }

    @Override
    public String getActuatorDescription() {
        return "Linphone VoIP notification actuator";
    }

    @Override
    public void triggerNotification(final DoorbellEvent doorbellEvent) {
        LOGGER.info("Received a DoorbellEvent :: Triggering VoIP call.");
        this.doCall();
    }

    private void doCall() {
        try {
            LOGGER.trace("Calling {} with timeout {}.", VOIP_NUMBER_TO_CALL, VOIP_CALL_TIMEOUT_SECONDS);
            final Process proc = Runtime.getRuntime().exec("linphonec -s " + VOIP_NUMBER_TO_CALL);

            // Kill process after timeout
            if(!proc.waitFor(VOIP_CALL_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                proc.destroy();
            }

            LOGGER.trace("Hang up");
        }
        catch (final IOException | InterruptedException e) {
            LOGGER.error("Exception while doing linphone call.", e);
        }
    }
}
