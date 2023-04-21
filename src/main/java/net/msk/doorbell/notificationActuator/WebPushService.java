package net.msk.doorbell.notificationActuator;

import jakarta.annotation.PostConstruct;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.apache.http.HttpResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class WebPushService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebPushService.class);

    @Value("${notificationActuator.webpush.vapid.publicKey}")
    private String publicKey;

    @Value("${notificationActuator.webpush.vapid.privateKey}")
    private String privateKey;

    private PushService pushService;

    private List<Subscription> subscriptions = new ArrayList<>();

    @PostConstruct
    public void init() throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        this.pushService = new PushService(this.publicKey, this.privateKey);
    }

    public String getPublicKey() {
        return this.publicKey;
    }

    public void subscribe(final Subscription subscription) {
        this.subscriptions.add(subscription);
    }

    public void unsubscribe(final String endpoint) {
        this.subscriptions = this.subscriptions.stream()
                .filter(s -> !endpoint.equals(s.endpoint))
                .collect(Collectors.toList());
    }

    public void sendNotification() {
        this.subscriptions.forEach((sub) -> {
            try {
                final Notification myNotification = Notification.builder()
                        .userPublicKey(sub.keys.p256dh)
                        .userAuth(sub.keys.auth)
                        .endpoint(sub.endpoint)
                        .payload("{\"title\": \"Doorbell!\",\"message\": \"Ring! Ring!\"}")
                        .ttl(60 * 60)
                        .build();
                final HttpResponse res = this.pushService.send(myNotification);
                LOGGER.info("Tried sending notification. Result: " + res.toString());
            }
            catch (GeneralSecurityException | InterruptedException | IOException | JoseException |
                   ExecutionException e) {
                LOGGER.error("Sending notification failed.", e);
            }
        });
    }
}
