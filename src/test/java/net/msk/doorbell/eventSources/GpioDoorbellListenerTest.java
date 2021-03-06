package net.msk.doorbell.eventSources;

import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import net.msk.doorbell.DoorbellEvent;
import net.msk.doorbell.service.DoorbellService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GpioDoorbellListenerTest {

    private DoorbellService doorbellService;
    private GpioDoorbellListener gpioDoorbellListener;

    @BeforeEach
    void setUp() {
        this.doorbellService = mock(DoorbellService.class);
        this.gpioDoorbellListener = new GpioDoorbellListener(this.doorbellService);
    }

    @Test
    void handleGpioPinDigitalStateChangeEvent() {
        final GpioPinDigitalStateChangeEvent gpioEvent = mock(GpioPinDigitalStateChangeEvent.class);

        final GpioPin pin = mock(GpioPin.class);
        Mockito.when(pin.getName()).thenReturn("Pin1");
        Mockito.when(gpioEvent.getPin()).thenReturn(pin);

        Mockito.when(gpioEvent.getState()).thenReturn(PinState.LOW);
        this.gpioDoorbellListener.handleGpioPinDigitalStateChangeEvent(gpioEvent);
        verifyNoInteractions(this.doorbellService);

        Mockito.when(gpioEvent.getState()).thenReturn(PinState.HIGH);
        this.gpioDoorbellListener.handleGpioPinDigitalStateChangeEvent(gpioEvent);

        final ArgumentCaptor<DoorbellEvent> argument = ArgumentCaptor.forClass(DoorbellEvent.class);
        verify(this.doorbellService, times(1)).processEvent(argument.capture());
        assertEquals("Pin1.ring", argument.getValue().getEventQualifier());
        assertEquals("Doorbell rang.", argument.getValue().getEventDescription());
    }
}