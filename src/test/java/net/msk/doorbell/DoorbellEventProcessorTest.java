package net.msk.doorbell;

import net.msk.doorbell.notificationActuator.LinphoneNotificationActuatorSingleton;
import net.msk.doorbell.notificationActuator.NotificationActuatorType;
import net.msk.doorbell.persistance.EventLogRepository;
import net.msk.doorbell.service.DoorbellEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class DoorbellEventProcessorTest {

    private DoorbellEventService doorbellEventService;

    @BeforeEach
    void setUp() {
        final EventLogRepository eventLogRepository = mock(EventLogRepository.class);
        this.doorbellEventService = new DoorbellEventService(eventLogRepository, "voip,mail");
    }

    @Test
    void processEvent() {
        final LinphoneNotificationActuatorSingleton linphoneNotificationActuatorSingleton = mock(LinphoneNotificationActuatorSingleton.class);
        Mockito.when(linphoneNotificationActuatorSingleton.getType()).thenReturn(NotificationActuatorType.voip);

        final DoorbellEvent doorbellEvent = new DoorbellEvent("EventSource", "This is a unit test event");

        this.doorbellEventService.registerNotificationActuator(linphoneNotificationActuatorSingleton);
        this.doorbellEventService.processEvent(doorbellEvent);

        verify(linphoneNotificationActuatorSingleton, times(1)).getType();
        verify(linphoneNotificationActuatorSingleton, times(1)).getActuatorDescription();
        verify(linphoneNotificationActuatorSingleton, times(1)).triggerNotification(doorbellEvent);
    }
}