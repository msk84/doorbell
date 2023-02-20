package net.msk.doorbell.notificationActuator;

import net.msk.doorbell.service.DoorbellService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.MailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailNotificationActuatorTest {

    private EmailNotificationActuator emailNotificationActuator;

    @BeforeEach
    void setUp() {
        final DoorbellService doorbellService = mock(DoorbellService.class);
        final MailSender mailSender = mock(MailSender.class);
        this.emailNotificationActuator = new EmailNotificationActuator(doorbellService, mailSender);
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