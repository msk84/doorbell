package net.msk.doorbell.controller;

import net.msk.doorbell.service.EventLogService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eventhistory")
public class EventHistoryController {

    private final EventLogService eventLogService;

    public EventHistoryController(EventLogService eventLogService) {
        this.eventLogService = eventLogService;
    }

    @DeleteMapping
    public void deleteEventHistory() {
        this.eventLogService.deleteEventHistory();
    }
}
