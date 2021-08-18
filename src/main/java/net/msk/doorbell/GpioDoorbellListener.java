package net.msk.doorbell;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GpioDoorbellListener implements GpioPinListenerDigital {

    private static final Logger LOGGER = LoggerFactory.getLogger(GpioDoorbellListener.class);

    private LinphoneControllerSingleton linphoneControllerSingleton;

    GpioDoorbellListener(final LinphoneControllerSingleton linphoneControllerSingleton) {
        this.linphoneControllerSingleton = linphoneControllerSingleton;
    }

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        LOGGER.trace("RingRing :: Pin {} went to state {}.", event.getPin(), event.getState());
        this.linphoneControllerSingleton.doCall();
    }
}
