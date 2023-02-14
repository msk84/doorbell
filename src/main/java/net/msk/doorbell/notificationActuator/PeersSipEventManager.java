package net.msk.doorbell.notificationActuator;

import net.sourceforge.peers.Logger;
import net.sourceforge.peers.javaxsound.JavaxSoundManager;
import net.sourceforge.peers.sip.core.useragent.SipListener;
import net.sourceforge.peers.sip.core.useragent.UserAgent;
import net.sourceforge.peers.sip.syntaxencoding.SipUriSyntaxException;
import net.sourceforge.peers.sip.transport.SipRequest;
import net.sourceforge.peers.sip.transport.SipResponse;

import java.net.SocketException;

public class PeersSipEventManager implements SipListener {

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
                e.printStackTrace();
            }
        }).start();
    }

    public void callDefaultCallee() {
        this.call(this.peersCustomConfig.getDefaultNotificationNumber());
    }

    public void call(final String callee) {
        new Thread(() -> {
            try {
                this.sipRequest = this.userAgent.invite(callee, null);
            } catch (final SipUriSyntaxException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void hangup() {
        new Thread(() -> this.userAgent.terminate(this.sipRequest)).start();
    }

    @Override
    public void registering(final SipRequest sipRequest) {
    }

    @Override
    public void registerSuccessful(final SipResponse sipResponse) {
    }

    @Override
    public void registerFailed(final SipResponse sipResponse) {
    }

    @Override
    public void incomingCall(final SipRequest sipRequest, final SipResponse sipResponse) {
    }

    @Override
    public void remoteHangup(final SipRequest sipRequest) {
    }

    @Override
    public void ringing(final SipResponse sipResponse) {
    }

    @Override
    public void calleePickup(final SipResponse sipResponse) {
    }

    @Override
    public void error(final SipResponse sipResponse) {
    }
}