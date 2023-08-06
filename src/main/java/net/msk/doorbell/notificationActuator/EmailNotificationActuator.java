package net.msk.doorbell.notificationActuator;

import net.msk.doorbell.DoorbellEvent;
import net.msk.doorbell.service.DoorbellService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
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

    private final MailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailSenderUser;

    @Value("${notificationActuator.mail.recipients}")
    private String mailRecipients;

    public EmailNotificationActuator(final DoorbellService doorbellService, final MailSender mailSender) {
        doorbellService.registerNotificationActuator(this);
        this.mailSender = mailSender;
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
    public boolean notify(final DoorbellEvent doorbellEvent) {
        LOGGER.info("Will do email notification for event: {}", doorbellEvent);
        try {
            return this.sendNotificationMail(doorbellEvent);
        }
        catch (final Exception e) {
            LOGGER.error("Failed to send notification email. Please check your configuration.", e);
        }
        return false;
    }

    private boolean sendNotificationMail(final DoorbellEvent doorbellEvent) {
        final SimpleMailMessage message = new SimpleMailMessage();
        if(emailPattern.matcher(this.mailSenderUser).matches() && emailPattern.matcher(this.mailRecipients).matches()) {
            message.setFrom(this.mailSenderUser);
            message.setTo(this.mailRecipients);
            message.setSubject("Doorbell notification :: " + doorbellEvent.getEventDescription());
            final String mailText = "Event timestamp: " +
                    doorbellEvent.getTimestamp() +
                    "\nEvent qualifier: " +
                    doorbellEvent.getEventQualifier() +
                    "\nEvent description: " +
                    doorbellEvent.getEventDescription();
            message.setText(mailText);
            this.mailSender.send(message);
            LOGGER.trace("Notification mail sent to {}.", this.mailRecipients);
            return true;
        }
        else {
            LOGGER.error("Invalid mail sender or mail recipient configuration.");
            return false;
        }
    }
}
