package net.msk.doorbell;

import net.msk.doorbell.notificationActuator.PeersSipPhoneActuator;
import net.msk.doorbell.notificationActuator.NotificationActuatorType;
import net.msk.doorbell.persistance.EventLogRepository;
import net.msk.doorbell.service.DoorbellService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class DoorbellEventProcessorTest {

    private DoorbellService doorbellService;

    @BeforeEach
    void setUp() {
        final EventLogRepository eventLogRepository = mock(EventLogRepository.class);
        this.doorbellService = new DoorbellService(eventLogRepository, "voip,mail");
    }

    @Test
    void processEvent() {
        final PeersSipPhoneActuator peersSipPhoneNotificationActuatorSingleton = mock(PeersSipPhoneActuator.class);
        Mockito.when(peersSipPhoneNotificationActuatorSingleton.getType()).thenReturn(NotificationActuatorType.voip);

        final DoorbellEvent doorbellEvent = new DoorbellEvent("EventSource", "This is a unit test event");

        this.doorbellService.registerNotificationActuator(peersSipPhoneNotificationActuatorSingleton);
        this.doorbellService.processEvent(doorbellEvent);

        verify(peersSipPhoneNotificationActuatorSingleton, times(1)).getType();
        verify(peersSipPhoneNotificationActuatorSingleton, times(1)).getActuatorDescription();
        verify(peersSipPhoneNotificationActuatorSingleton, times(1)).notify(doorbellEvent);
    }
}