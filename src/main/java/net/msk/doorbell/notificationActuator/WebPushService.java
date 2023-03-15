package net.msk.doorbell.notificationActuator;

import jakarta.annotation.PostConstruct;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.apache.http.HttpResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
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
        Subscription theSub = this.subscriptions.get(0);

        try {
            final Notification myNotification = Notification.builder()
                    .userPublicKey(theSub.keys.p256dh)
                    .userAuth(theSub.keys.auth)
                    .endpoint(theSub.endpoint)
                    .payload("{\"title\": \"Server says hello!\",\"body\": \"It is now: %s\"}")
                    .ttl(60*60)
                    .build();
            final HttpResponse res = this.pushService.send(myNotification);
        } catch (GeneralSecurityException | InterruptedException | IOException | JoseException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
