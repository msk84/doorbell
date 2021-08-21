package net.msk.doorbell;

import net.msk.doorbell.notificationActuator.LinphoneControllerSingleton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class DoorbellEventProcessorTest {

    private DoorbellEventProcessor doorbellEventProcessor;

    @BeforeEach
    void setUp() {
        this.doorbellEventProcessor = new DoorbellEventProcessor();
    }

    @Test
    void processEvent() {
        final LinphoneControllerSingleton linphoneControllerSingleton = mock(LinphoneControllerSingleton.class);
        final DoorbellEvent doorbellEvent = new DoorbellEvent("EventSource", "This is a unit test event");

        this.doorbellEventProcessor.registerNotificationActuator(linphoneControllerSingleton);
        this.doorbellEventProcessor.processEvent(doorbellEvent);

        verify(linphoneControllerSingleton, times(1)).triggerNotification(doorbellEvent);
    }
}