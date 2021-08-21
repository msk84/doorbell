package net.msk.doorbell.notificationActuator;

import net.msk.doorbell.DoorbellEvent;

public interface NotificationActuator {

    String getActuatorDescription();

    void triggerNotification(DoorbellEvent doorbellEvent);

}
