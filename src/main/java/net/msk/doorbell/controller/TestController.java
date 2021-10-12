package net.msk.doorbell.controller;

import net.msk.doorbell.DoorbellEvent;
import net.msk.doorbell.service.DoorbellService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    private final DoorbellService doorbellService;

    public TestController(final DoorbellService doorbellService) {
        this.doorbellService = doorbellService;
    }

    @PostMapping("/event")
    public void triggerTestEvent() {
        LOGGER.info("Triggering test event now.");
        this.doorbellService.processEvent(new DoorbellEvent("test.door.1.ring", "Test doorbell 1 rang."));
    }

    @PostMapping("/open")
    public void triggerTestOpen() {
        LOGGER.info("Triggering open door action now.");
        this.doorbellService.openDoor();
    }
}
