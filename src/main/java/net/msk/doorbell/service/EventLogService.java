package net.msk.doorbell.service;

import net.msk.doorbell.persistance.EventLogItemEntity;
import net.msk.doorbell.persistance.EventLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventLogService {

    private final EventLogRepository eventLogRepository;

    public EventLogService(final EventLogRepository eventLogRepository) {
        this.eventLogRepository = eventLogRepository;
    }

    public EventLogItemEntity addEventLog(final EventLogItemEntity eventLogItemEntity) {
        return this.eventLogRepository.save(eventLogItemEntity);
    }

    public List<EventLogItemEntity> getEventLog() {
        return this.eventLogRepository.findAll();
    }
}
