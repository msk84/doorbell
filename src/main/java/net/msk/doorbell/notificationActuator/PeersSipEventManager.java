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

    private final UserAgent userAgent;
    private SipRequest sipRequest;

    public PeersSipEventManager() throws SocketException {
        final Logger logger = new PeersLogger();
        final PeersCustomConfig config = new PeersCustomConfig();
        final JavaxSoundManager javaxSoundManager = new JavaxSoundManager(false, logger, null);
        userAgent = new UserAgent(this, config, logger, javaxSoundManager);
        new Thread(() -> {
            try {
                userAgent.register();
            } catch (SipUriSyntaxException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void callDefaultCallee() {
        this.call("sip:**610@fritz.box");
    }

    public void call(final String callee) {
        new Thread(() -> {
            try {
                sipRequest = userAgent.invite(callee, null);
            } catch (final SipUriSyntaxException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void hangup() {
        new Thread(() -> userAgent.terminate(sipRequest)).start();
    }

    @Override
    public void registering(SipRequest sipRequest) {
    }

    @Override
    public void registerSuccessful(SipResponse sipResponse) {
    }

    @Override
    public void registerFailed(SipResponse sipResponse) {
    }

    @Override
    public void incomingCall(SipRequest sipRequest, SipResponse sipResponse) {
    }

    @Override
    public void remoteHangup(SipRequest sipRequest) {
    }

    @Override
    public void ringing(SipResponse sipResponse) {
    }

    @Override
    public void calleePickup(SipResponse sipResponse) {
    }

    @Override
    public void error(SipResponse sipResponse) {
    }
}
