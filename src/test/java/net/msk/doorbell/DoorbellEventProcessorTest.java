package net.msk.doorbell;

import net.msk.doorbell.eventSources.GpioControllerSingleton;
import net.msk.doorbell.notificationActuator.LinphoneNotificationActuatorSingleton;
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
        final GpioControllerSingleton gpioControllerSingleton = mock(GpioControllerSingleton.class);
        this.doorbellService = new DoorbellService(eventLogRepository, "voip,mail");
    }

    @Test
    void processEvent() {
        final LinphoneNotificationActuatorSingleton linphoneNotificationActuatorSingleton = mock(LinphoneNotificationActuatorSingleton.class);
        Mockito.when(linphoneNotificationActuatorSingleton.getType()).thenReturn(NotificationActuatorType.voip);

        final DoorbellEvent doorbellEvent = new DoorbellEvent("EventSource", "This is a unit test event");

        this.doorbellService.registerNotificationActuator(linphoneNotificationActuatorSingleton);
        this.doorbellService.processEvent(doorbellEvent);

        verify(linphoneNotificationActuatorSingleton, times(1)).getType();
        verify(linphoneNotificationActuatorSingleton, times(1)).getActuatorDescription();
        verify(linphoneNotificationActuatorSingleton, times(1)).triggerNotification(doorbellEvent);
    }
}