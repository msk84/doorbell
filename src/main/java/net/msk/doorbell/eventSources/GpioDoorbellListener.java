package net.msk.doorbell.eventSources;

import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import net.msk.doorbell.DoorbellEvent;
import net.msk.doorbell.service.DoorbellEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GpioDoorbellListener implements GpioPinListenerDigital {

    private static final Logger LOGGER = LoggerFactory.getLogger(GpioDoorbellListener.class);

    private DoorbellEventService doorbellEventService;

    GpioDoorbellListener(final DoorbellEventService doorbellEventService) {
        this.doorbellEventService = doorbellEventService;
    }

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        if(event.getState() == PinState.HIGH) {
            LOGGER.trace("RingRing! :: Pin {} went to state {}.", event.getPin(), event.getState());
            final DoorbellEvent doorbellEvent = new DoorbellEvent("doorbell.1.ring", "Doorbell number one rang.");
            this.doorbellEventService.processEvent(doorbellEvent);
        }
    }
}
