package net.msk.doorbell.notificationActuator;

import net.msk.doorbell.DoorbellEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailNotificationActuator implements NotificationActuator {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationActuator.class);

    @Override
    public String getActuatorDescription() {
        return "Email notification actuator";
    }

    @Override
    public void triggerNotification(final DoorbellEvent doorbellEvent) {
        // I should send out an email now...
        LOGGER.info("Would do email notification for event: {}", doorbellEvent);
    }
}
