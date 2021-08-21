package net.msk.doorbell;

import net.msk.doorbell.notificationActuator.NotificationActuator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DoorbellEventProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DoorbellEventProcessor.class);

    final List<NotificationActuator> activeActuators = new ArrayList<>();

    public void registerNotificationActuator(final NotificationActuator notificationActuator) {
        LOGGER.info("Registered '{}'.", notificationActuator.getActuatorDescription());
        this.activeActuators.add(notificationActuator);
    }

    public void processEvent(final DoorbellEvent doorbellEvent) {
        LOGGER.trace("Processing DoorbellEvent: {}", doorbellEvent);
        activeActuators.forEach(na -> na.triggerNotification(doorbellEvent));
    }
}
