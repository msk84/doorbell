package net.msk.doorbell.eventSources;

import com.pi4j.io.gpio.*;
import net.msk.doorbell.DoorbellEventProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Profile("!dev")
public class GpioControllerSingleton implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(GpioControllerSingleton.class);

    @Value("${doorbell.pin.wiringpi_address:1}")
    private int DOORBELL_INPUT_PIN_ADDRESS;

    @Value("${doorbell.pin.debounce_delay_millis:1000}")
    private int DOORBELL_DEBOUNCE_DELAY_TIME_MILLIS;

    private final GpioController gpio;
    private final DoorbellEventProcessor doorbellEventProcessor;

    public GpioControllerSingleton(final DoorbellEventProcessor doorbellEventProcessor) {
        LOGGER.trace("Created bean 'GpioControllerSingleton'.");
        this.gpio = GpioFactory.getInstance();
        this.doorbellEventProcessor = doorbellEventProcessor;
    }

    @Override
    public void afterPropertiesSet() {
        this.initializePins();
    }

    private void initializePins() {
        LOGGER.trace("Configured properties:: DoorbellPinWiringpi_address: {}, DoorbellPinDebounce_delay_millis {}",DOORBELL_INPUT_PIN_ADDRESS, DOORBELL_DEBOUNCE_DELAY_TIME_MILLIS);

        Pin doorbellInputPin = RaspiPin.getPinByAddress(DOORBELL_INPUT_PIN_ADDRESS);
        if(doorbellInputPin == null) {
            LOGGER.warn("Failed to resolve configured WiringPi-GPIO pin '{}'. Using 'WiringPi-GPIO 1' instead.", DOORBELL_INPUT_PIN_ADDRESS);
            doorbellInputPin = RaspiPin.GPIO_01;
        }

        final GpioPinDigitalInput doorbellInput = gpio.provisionDigitalInputPin(doorbellInputPin, "doorbellInput", PinPullResistance.PULL_DOWN);
        doorbellInput.setDebounce(DOORBELL_DEBOUNCE_DELAY_TIME_MILLIS);
        doorbellInput.addListener(new GpioDoorbellListener(this.doorbellEventProcessor));
    }
}
