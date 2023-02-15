package net.msk.doorbell.notificationActuator;

import net.sourceforge.peers.Config;
import net.sourceforge.peers.media.MediaMode;
import net.sourceforge.peers.sip.syntaxencoding.SipURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class PeersCustomConfig implements Config {

    @Value("${notificationActuator.voip.host_ip_address}")
    private String NOTIFICATIONACTUATOR_VOIP_HOSTIPADDRESS;

    @Value("${notificationActuator.voip.registrar}")
    private String NOTIFICATIONACTUATOR_VOIP_REGISTRAR;

    @Value("${notificationActuator.voip.user_name}")
    private String NOTIFICATIONACTUATOR_VOIP_USERNAME;

    @Value("${notificationActuator.voip.password}")
    private String NOTIFICATIONACTUATOR_VOIP_PASSWORD;

    @Value("${notificationActuator.voip.call.default_number}")
    private String NOTIFICATIONACTUATOR_VOIP_CALL_DEFAULTNUMBER;

    @Value("${notificationActuator.voip.call.hangup_delay_seconds}")
    private long NOTIFICATIONACTUATOR_VOIP_CALL_HANGUPDELAYSECONDS;


    private InetAddress publicIpAddress;

    @Override
    public void save() {
    }

    @Override
    public InetAddress getLocalInetAddress() {
        try {
            return InetAddress.getByName(this.NOTIFICATIONACTUATOR_VOIP_HOSTIPADDRESS);
        }
        catch (final UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InetAddress getPublicInetAddress() {
        return this.publicIpAddress;
    }

    @Override
    public String getUserPart() {
        return this.NOTIFICATIONACTUATOR_VOIP_USERNAME;
    }

    @Override
    public String getDomain() {
        return this.NOTIFICATIONACTUATOR_VOIP_REGISTRAR;
    }

    @Override
    public String getPassword() {
        return this.NOTIFICATIONACTUATOR_VOIP_PASSWORD;
    }

    @Override
    public SipURI getOutboundProxy() {
        return null;
    }

    @Override
    public int getSipPort() {
        return 0;
    }

    @Override
    public MediaMode getMediaMode() {
        return MediaMode.none;
    }

    @Override
    public boolean isMediaDebug() {
        return false;
    }

    @Override
    public String getMediaFile() {
        return null;
    }

    @Override
    public int getRtpPort() {
        return 0;
    }

    @Override
    public String getAuthorizationUsername() {
        return this.getUserPart();
    }

    @Override
    public void setLocalInetAddress(final InetAddress inetAddress) {
    }

    @Override
    public void setPublicInetAddress(final InetAddress inetAddress) {
        this.publicIpAddress = inetAddress;
    }

    @Override
    public void setUserPart(final String s) {
    }

    @Override
    public void setDomain(final String s) {
    }

    @Override
    public void setPassword(final String s) {
    }

    @Override
    public void setOutboundProxy(final SipURI sipURI) {
    }

    @Override
    public void setSipPort(final int i) {
    }

    @Override
    public void setMediaMode(final MediaMode mediaMode) {
    }

    @Override
    public void setMediaDebug(final boolean b) {
    }

    @Override
    public void setMediaFile(final String s) {
    }

    @Override
    public void setRtpPort(final int i) {
    }

    @Override
    public void setAuthorizationUsername(final String s) {
    }

    public String getDefaultNotificationNumber() {
        return NOTIFICATIONACTUATOR_VOIP_CALL_DEFAULTNUMBER;
    }

    public long getHangupDelay() {
        return NOTIFICATIONACTUATOR_VOIP_CALL_HANGUPDELAYSECONDS;
    }
}
