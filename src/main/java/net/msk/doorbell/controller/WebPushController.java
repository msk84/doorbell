package net.msk.doorbell.controller;

import net.msk.doorbell.notificationActuator.WebPushService;
import nl.martijndwars.webpush.Subscription;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webpush")
public class WebPushController {

    private final WebPushService webPushService;

    public WebPushController(final WebPushService webPushService) {
        this.webPushService = webPushService;
    }

    @GetMapping("/publicKey")
    public String getPublicKey() {
        return this.webPushService.getPublicKey();
    }

    @PostMapping("/subscribe")
    public void subscribe(@RequestBody final Subscription subscription) {
        this.webPushService.subscribe(subscription);
    }

    @PostMapping("/unsubscribe")
    public void unsubscribe(final String endpoint) {
        this.webPushService.unsubscribe(endpoint);
    }

    @PostMapping("/notify")
    public void sendNotificationMessage() {
        this.webPushService.sendNotification();
    }
}
