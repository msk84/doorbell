package net.msk.doorbell.notificationActuator;

import net.msk.doorbell.service.DoorbellEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailNotificationActuatorTest {

    //private JavaMailSenderImpl javaMailSender;
    private EmailNotificationActuator emailNotificationActuator;

    @BeforeEach
    void setUp() {
        final DoorbellEventService doorbellEventService = mock(DoorbellEventService.class);
        //this.javaMailSender = mock(JavaMailSenderImpl.class);
        this.emailNotificationActuator = new EmailNotificationActuator(doorbellEventService);
    }

    @Test
    void getType() {
        assertEquals(NotificationActuatorType.mail, this.emailNotificationActuator.getType());
    }

    @Test
    void getActuatorDescription() {
        assertEquals("Email notification actuator", this.emailNotificationActuator.getActuatorDescription());
    }
}