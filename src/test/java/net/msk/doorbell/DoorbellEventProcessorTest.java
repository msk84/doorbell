package net.msk.doorbell;

import net.msk.doorbell.notificationActuator.LinphoneNotificationActuatorSingleton;
import net.msk.doorbell.persistance.EventLogRepository;
import net.msk.doorbell.service.DoorbellEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class DoorbellEventProcessorTest {

    private DoorbellEventService doorbellEventService;
    private EventLogRepository eventLogRepository;

    @BeforeEach
    void setUp() {
        this.eventLogRepository = mock(EventLogRepository.class);
        this.doorbellEventService = new DoorbellEventService(this.eventLogRepository);
    }

    @Test
    void processEvent() {
        final LinphoneNotificationActuatorSingleton linphoneNotificationActuatorSingleton = mock(LinphoneNotificationActuatorSingleton.class);
        final DoorbellEvent doorbellEvent = new DoorbellEvent("EventSource", "This is a unit test event");

        this.doorbellEventService.registerNotificationActuator(linphoneNotificationActuatorSingleton);
        this.doorbellEventService.processEvent(doorbellEvent);

        verify(linphoneNotificationActuatorSingleton, times(1)).triggerNotification(doorbellEvent);
    }
}