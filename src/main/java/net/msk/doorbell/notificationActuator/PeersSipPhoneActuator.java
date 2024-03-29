package net.msk.doorbell.notificationActuator;

import net.msk.doorbell.DoorbellEvent;
import net.msk.doorbell.service.DoorbellService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.SocketException;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@ConditionalOnProperty(
        value="notificationActuator.voip.enabled",
        havingValue = "true")
public class PeersSipPhoneActuator implements NotificationActuator {

    private static final Logger LOGGER = LoggerFactory.getLogger(PeersSipPhoneActuator.class);

    private final PeersSipEventManager eventManager;

    private final DoorbellService doorbellService;

    @Autowired
    public PeersSipPhoneActuator(final DoorbellService doorbellService, final PeersCustomConfig peersCustomConfig) throws SocketException {
        LOGGER.trace("Created bean 'PeersSipPhoneActuator'.");
        this.eventManager = new PeersSipEventManager(peersCustomConfig);
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
    public boolean notify(DoorbellEvent doorbellEvent) {
        LOGGER.info("Received a DoorbellEvent :: Triggering VoIP call.");
        return this.doNotificationCall();
    }

    private boolean doNotificationCall() {
        this.eventManager.callDefaultCalleeAndHangup();
        return true;
    }
}
