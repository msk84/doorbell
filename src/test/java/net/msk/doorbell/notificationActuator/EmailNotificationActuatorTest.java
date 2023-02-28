package net.msk.doorbell.notificationActuator;

import net.msk.doorbell.DoorbellEvent;
import net.msk.doorbell.service.DoorbellService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.*;

@ExtendWith(MockitoExtension.class)
class EmailNotificationActuatorTest {

    @Mock
    private DoorbellService doorbellService;

    @Mock
    private MailSender mailSender;

    private EmailNotificationActuator emailNotificationActuator;

    @BeforeEach
    void setUp() {
        this.emailNotificationActuator = spy(new EmailNotificationActuator(this.doorbellService, this.mailSender));
        setField(this.emailNotificationActuator, "mailSenderUser", "sender@test.com");
        setField(this.emailNotificationActuator, "mailRecipients", "recipient@test.com");
    }

    @Test
    void testGetType() {
        assertEquals(NotificationActuatorType.mail, this.emailNotificationActuator.getType());
    }

    @Test
    void testGetActuatorDescription() {
        assertEquals("Email notification actuator", this.emailNotificationActuator.getActuatorDescription());
    }

    @Test
    void testNotify() {
        final DoorbellEvent doorbellTestEvent = new DoorbellEvent("event.qualifier", "This is a test event.");
        this.emailNotificationActuator.notify(doorbellTestEvent);
        verify(this.mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}