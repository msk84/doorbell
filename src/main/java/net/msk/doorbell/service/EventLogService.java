package net.msk.doorbell.service;

import net.msk.doorbell.persistance.EventLogItemEntity;
import net.msk.doorbell.persistance.EventLogRepository;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventLogService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(EventLogService.class);

    private final EventLogRepository eventLogRepository;

    public EventLogService(final EventLogRepository eventLogRepository) {
        this.eventLogRepository = eventLogRepository;
    }

    public EventLogItemEntity addEventLog(final EventLogItemEntity eventLogItemEntity) {
        return this.eventLogRepository.save(eventLogItemEntity);
    }

    public List<EventLogItemEntity> getEventLog() {
        return this.eventLogRepository.findAll(Sort.by("timestamp").descending());
    }

    public void deleteEventHistory() {
        LOGGER.info("Delete all event history.");
        this.eventLogRepository.deleteAll();
    }
}
