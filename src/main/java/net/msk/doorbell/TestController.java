package net.msk.doorbell;

import net.msk.doorbell.notificationActuator.LinphoneControllerSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    private final LinphoneControllerSingleton linphoneControllerSingleton;

    public TestController(final LinphoneControllerSingleton linphoneControllerSingleton) {
        this.linphoneControllerSingleton = linphoneControllerSingleton;
    }

    @GetMapping("/call")
    public void doTestCall() {
        LOGGER.info("Doing a test call now.");
        this.linphoneControllerSingleton.triggerNotification(new DoorbellEvent("TestController", "TestEvent was triggered by TestController"));
    }
}
