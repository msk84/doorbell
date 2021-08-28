package net.msk.doorbell.service;

import net.msk.doorbell.DoorbellEvent;
import net.msk.doorbell.notificationActuator.NotificationActuator;
import net.msk.doorbell.persistance.EventLogItemEntity;
import net.msk.doorbell.persistance.EventLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoorbellEventService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DoorbellEventService.class);

    private final List<NotificationActuator> activeActuators = new ArrayList<>();
    private final EventLogRepository eventLogRepository;

    public DoorbellEventService(final EventLogRepository eventLogRepository) {
        this.eventLogRepository = eventLogRepository;
    }

    public void registerNotificationActuator(final NotificationActuator notificationActuator) {
        LOGGER.info("Registered '{}'.", notificationActuator.getActuatorDescription());
        this.activeActuators.add(notificationActuator);
    }

    public void processEvent(final DoorbellEvent doorbellEvent) {
        LOGGER.trace("Processing DoorbellEvent: {}", doorbellEvent);
        final EventLogItemEntity eventLogItem = new EventLogItemEntity(doorbellEvent.getEventQualifier(), doorbellEvent.getEventDescription());
        this.eventLogRepository.save(eventLogItem);
        activeActuators.forEach(na -> na.triggerNotification(doorbellEvent));
    }
}
