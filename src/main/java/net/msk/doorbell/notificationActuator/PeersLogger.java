package net.msk.doorbell.notificationActuator;

import net.sourceforge.peers.Logger;
import org.slf4j.LoggerFactory;

public class PeersLogger implements Logger {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PeersLogger.class);

    @Override
    public void debug(final String message) {
        LOGGER.debug(message);
    }

    @Override
    public void info(final String message) {
        LOGGER.info(message);
    }

    @Override
    public void error(final String message) {
        LOGGER.error(message);
    }

    @Override
    public void error(final String message, final Exception e) {
        LOGGER.error(message, e);
    }

    @Override
    public void traceNetwork(final String message, final String direction) {
        LOGGER.trace(direction + "::" + message);
    }
}
