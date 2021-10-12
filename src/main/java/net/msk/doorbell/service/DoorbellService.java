package net.msk.doorbell.service;

import net.msk.doorbell.DoorbellEvent;
import net.msk.doorbell.notificationActuator.NotificationActuator;
import net.msk.doorbell.notificationActuator.NotificationActuatorType;
import net.msk.doorbell.persistance.EventLogItemEntity;
import net.msk.doorbell.persistance.EventLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoorbellService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DoorbellService.class);

    private final List<NotificationActuator> activeActuators = new ArrayList<>();
    private final EventLogRepository eventLogRepository;
    private final List<NotificationActuatorType> notificationActuatorTypeWhitelist;

    public DoorbellService(final EventLogRepository eventLogRepository,
                           @Value("${doorbell.notificationActuators}") final String activatedNotificationActuatorConfigString) {
        this.eventLogRepository = eventLogRepository;
        this.notificationActuatorTypeWhitelist = Arrays.stream(activatedNotificationActuatorConfigString.split(","))
                .map(NotificationActuatorType::valueOf)
                .collect(Collectors.toList());
    }

    public void registerNotificationActuator(final NotificationActuator notificationActuator) {
        LOGGER.info("Registered '{}'.", notificationActuator.getActuatorDescription());
        if(this.notificationActuatorTypeWhitelist.contains(notificationActuator.getType())) {
            this.activeActuators.add(notificationActuator);
        }
    }

    public void processEvent(final DoorbellEvent doorbellEvent) {
        LOGGER.trace("Processing DoorbellEvent: {}", doorbellEvent);
        final EventLogItemEntity eventLogItem = new EventLogItemEntity(doorbellEvent.getEventQualifier(), doorbellEvent.getEventDescription());
        this.eventLogRepository.save(eventLogItem);
        this.activeActuators.forEach(na -> na.triggerNotification(doorbellEvent));
    }

    public void openDoor() {
        LOGGER.trace("Door would be opened here...");
    }
}
