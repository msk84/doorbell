package net.msk.doorbell.notificationActuator;

import net.sourceforge.peers.Logger;
import net.sourceforge.peers.javaxsound.JavaxSoundManager;
import net.sourceforge.peers.sip.core.useragent.SipListener;
import net.sourceforge.peers.sip.core.useragent.UserAgent;
import net.sourceforge.peers.sip.syntaxencoding.SipUriSyntaxException;
import net.sourceforge.peers.sip.transport.SipRequest;
import net.sourceforge.peers.sip.transport.SipResponse;
import org.slf4j.LoggerFactory;

import java.net.SocketException;

public class PeersSipEventManager implements SipListener {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PeersSipEventManager.class);

    private final PeersCustomConfig peersCustomConfig;

    private final UserAgent userAgent;
    private SipRequest sipRequest;

    public PeersSipEventManager(final PeersCustomConfig peersCustomConfig) throws SocketException {
        this.peersCustomConfig = peersCustomConfig;
        final Logger logger = new PeersLogger();
        final JavaxSoundManager javaxSoundManager = new JavaxSoundManager(false, logger, null);
        userAgent = new UserAgent(this, this.peersCustomConfig, logger, javaxSoundManager);
        new Thread(() -> {
            try {
                userAgent.register();
            } catch (SipUriSyntaxException e) {
                LOGGER.error("Failed to register due to invalid notification number.", e);
            }
        }).start();
    }

    public void callDefaultCalleeAndHangup() {
        new Thread(() -> {
            try {
                final String callee = this.peersCustomConfig.getDefaultNotificationNumber();
                LOGGER.info("Do call to {}.", callee);
                this.sipRequest = this.userAgent.invite(callee, null);
                LOGGER.info("Trigger delayed hangup.");
                this.delayedHangup(this.peersCustomConfig.getHangupDelay());
            } catch (final SipUriSyntaxException e) {
                LOGGER.error("Failed to call due to invalid notification number.", e);
            }
        }).start();
    }

    private void delayedHangup(final long seconds) {
        new Thread(() -> {
            try {
                Thread.sleep(1000 * seconds);
                this.hangup();
            } catch (final InterruptedException e) {
                LOGGER.error("Delayed hangup was interrupted.", e);
            }
        }).start();
    }

    public void call(final String callee) {
        new Thread(() -> {
            try {
                LOGGER.info("Do call to {}.", callee);
                this.sipRequest = this.userAgent.invite(callee, null);
            } catch (final SipUriSyntaxException e) {
                LOGGER.error("Failed to call due to invalid notification number.", e);
            }
        }).start();
    }

    public void hangup() {
        LOGGER.info("Hangup!");
        new Thread(() -> this.userAgent.terminate(this.sipRequest)).start();
    }

    @Override
    public void registering(final SipRequest sipRequest) {
    }

    @Override
    public void registerSuccessful(final SipResponse sipResponse) {
        LOGGER.info("SIP registration was successful.");
    }

    @Override
    public void registerFailed(final SipResponse sipResponse) {
        LOGGER.error("SIP registraiton failed.");
    }

    @Override
    public void incomingCall(final SipRequest sipRequest, final SipResponse sipResponse) {
        LOGGER.info("Incoming call!");
    }

    @Override
    public void remoteHangup(final SipRequest sipRequest) {
        LOGGER.info("Remote hangup!");
    }

    @Override
    public void ringing(final SipResponse sipResponse) {
        LOGGER.info("Ringing!");
    }

    @Override
    public void calleePickup(final SipResponse sipResponse) {
        LOGGER.info("Callee picked up!");
    }

    @Override
    public void error(final SipResponse sipResponse) {
        LOGGER.error("Error...");
    }
}
