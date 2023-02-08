package net.msk.doorbell.notificationActuator;

import net.msk.doorbell.DoorbellEvent;
import net.msk.doorbell.service.DoorbellService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.SocketException;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
//@Profile("!dev")
public class PeersSipPhoneActuator implements NotificationActuator {

    private static final Logger LOGGER = LoggerFactory.getLogger(PeersSipPhoneActuator.class);

    private PeersSipEventManager eventManager;

    private DoorbellService doorbellService;

    public PeersSipPhoneActuator(final DoorbellService doorbellService) throws SocketException {
        LOGGER.trace("Created bean 'PeersSipPhoneActuator'.");
        this.eventManager = new PeersSipEventManager();
        this.doorbellService = doorbellService;
        this.doorbellService.registerNotificationActuator(this);
    }

    @Override
    public NotificationActuatorType getType() {
        return NotificationActuatorType.voip;
    }

    @Override
    public String getActuatorDescription() {
        return "Peers VoIP notification actuator";
    }

    @Override
    public void triggerNotification(DoorbellEvent doorbellEvent) {
        LOGGER.info("Received a DoorbellEvent :: Triggering VoIP call.");
        this.doNotificationCall();
    }

    private void doNotificationCall() {
        this.eventManager.callDefaultCallee();
    }
}
