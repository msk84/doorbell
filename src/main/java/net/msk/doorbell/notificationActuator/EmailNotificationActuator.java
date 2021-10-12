package net.msk.doorbell.notificationActuator;

import net.msk.doorbell.DoorbellEvent;
import net.msk.doorbell.service.DoorbellService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@ConditionalOnProperty(
        value="mail.enabled",
        havingValue = "true")
public class EmailNotificationActuator implements NotificationActuator {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationActuator.class);

    private static final String EMAIL_REGEX = "^(.+)@(.+)$";
    private static final Pattern emailPattern = Pattern.compile(EMAIL_REGEX);

    private JavaMailSenderImpl mailService;

    @Value("${spring.mail.username}")
    private String mailSender;

    @Value("${doorbell.notification.mail.recipients}")
    private String mailRecipients;

    public EmailNotificationActuator(final DoorbellService doorbellService) {
        doorbellService.registerNotificationActuator(this);
        this.mailService = new JavaMailSenderImpl();
    }

    @Override
    public NotificationActuatorType getType() {
        return NotificationActuatorType.mail;
    }

    @Override
    public String getActuatorDescription() {
        return "Email notification actuator";
    }

    @Override
    public void triggerNotification(final DoorbellEvent doorbellEvent) {
        LOGGER.info("Would do email notification for event: {}", doorbellEvent);
        try {
            this.sendNotificationMail(doorbellEvent);
        }
        catch (final Exception e) {
            LOGGER.error("Failed to send notification email. Please check your configuration.", e);
        }
    }

    private void sendNotificationMail(final DoorbellEvent doorbellEvent) {
        final SimpleMailMessage message = new SimpleMailMessage();
        if(emailPattern.matcher(this.mailSender).matches() && emailPattern.matcher(this.mailRecipients).matches()) {
            message.setFrom(this.mailSender);
            message.setTo(this.mailRecipients);
            message.setSubject("Doorbell notification :: " + doorbellEvent.getEventDescription());
            final String mailText = "Event timestamp: " +
                    doorbellEvent.getTimestamp() +
                    "\nEvent qualifier: " +
                    doorbellEvent.getEventQualifier() +
                    "\nEvent description: " +
                    doorbellEvent.getEventDescription();
            message.setText(mailText);
            this.mailService.send(message);
            LOGGER.trace("Notification mail sent to {}.", this.mailRecipients);
        }
        else {
            LOGGER.error("Invalid mail sender or mail recipient configuration.");
        }
    }
}
