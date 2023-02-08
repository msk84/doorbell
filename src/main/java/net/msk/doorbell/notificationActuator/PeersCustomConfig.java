package net.msk.doorbell.notificationActuator;

import net.sourceforge.peers.Config;
import net.sourceforge.peers.media.MediaMode;
import net.sourceforge.peers.sip.syntaxencoding.SipURI;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class PeersCustomConfig implements Config {

    private InetAddress publicIpAddress;

    @Override
    public void save() {

    }

    @Override
    public InetAddress getLocalInetAddress() {
        try {
            return InetAddress.getByName("192.168.178.65");
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
        return "DoorPiFon";
    }

    @Override
    public String getDomain() {
        return "fritz.box";
    }

    @Override
    public String getPassword() {
        return "aX?752bVm3";
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
        return null;
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
}
