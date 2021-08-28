package net.msk.doorbell.controller;

import net.msk.doorbell.DoorbellEvent;
import net.msk.doorbell.service.DoorbellEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    private final DoorbellEventService doorbellEventService;

    public TestController(final DoorbellEventService doorbellEventService) {
        this.doorbellEventService = doorbellEventService;
    }

    @GetMapping("/event")
    public void triggerTestEvent() {
        LOGGER.info("Triggering test event now.");
        this.doorbellEventService.processEvent(new DoorbellEvent("test.door.1.ring", "Test doorbell 1 rang."));
    }
}
