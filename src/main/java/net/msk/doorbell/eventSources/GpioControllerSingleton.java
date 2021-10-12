package net.msk.doorbell.eventSources;

import com.pi4j.io.gpio.*;
import net.msk.doorbell.service.DoorbellService;
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

    @Value("${doorbell.1.pin.wiringpi_address}")
    private Integer DOORBELL_1_INPUT_PIN_ADDRESS;

    @Value("${doorbell.2.pin.wiringpi_address}")
    private Integer DOORBELL_2_INPUT_PIN_ADDRESS;

    @Value("${doorbell.3.pin.wiringpi_address}")
    private Integer DOORBELL_3_INPUT_PIN_ADDRESS;

    @Value("${doorbell.pin.debounce_delay_millis:1000}")
    private int DOORBELL_DEBOUNCE_DELAY_TIME_MILLIS;

    private final GpioController gpio;
    private final DoorbellService doorbellService;

    public GpioControllerSingleton(final DoorbellService doorbellService) {
        LOGGER.trace("Created bean 'GpioControllerSingleton'.");
        this.gpio = GpioFactory.getInstance();
        this.doorbellService = doorbellService;
    }

    @Override
    public void afterPropertiesSet() {
        this.initializePins();
    }

    private void initializePins() {
        LOGGER.trace("Configured properties:: Doorbell_1_PinWiringpi_address: {}, Doorbell_2_PinWiringpi_address: {}, Doorbell_3_PinWiringpi_address: {}, DoorbellPinDebounce_delay_millis {}",
                DOORBELL_1_INPUT_PIN_ADDRESS, DOORBELL_2_INPUT_PIN_ADDRESS, DOORBELL_3_INPUT_PIN_ADDRESS, DOORBELL_DEBOUNCE_DELAY_TIME_MILLIS);

        if(DOORBELL_1_INPUT_PIN_ADDRESS != null) {
            this.registerDoorbellPin(DOORBELL_1_INPUT_PIN_ADDRESS, "doorbell_1");
            LOGGER.trace("Registered doorbell 1 for pin {}", DOORBELL_1_INPUT_PIN_ADDRESS);
        }
        if(DOORBELL_2_INPUT_PIN_ADDRESS != null) {
            this.registerDoorbellPin(DOORBELL_2_INPUT_PIN_ADDRESS, "doorbell_2");
            LOGGER.trace("Registered doorbell 2 for pin {}", DOORBELL_2_INPUT_PIN_ADDRESS);
        }
        if(DOORBELL_3_INPUT_PIN_ADDRESS != null) {
            this.registerDoorbellPin(DOORBELL_3_INPUT_PIN_ADDRESS, "doorbell_3");
            LOGGER.trace("Registered doorbell 3 for pin {}", DOORBELL_3_INPUT_PIN_ADDRESS);
        }
    }

    private void registerDoorbellPin(final Integer address, final String description) {
        final Pin doorbellInputPin = RaspiPin.getPinByAddress(address);
        if (doorbellInputPin != null) {
            final GpioPinDigitalInput doorbellInput = gpio.provisionDigitalInputPin(doorbellInputPin, description, PinPullResistance.PULL_DOWN);
            doorbellInput.setDebounce(DOORBELL_DEBOUNCE_DELAY_TIME_MILLIS);
            doorbellInput.addListener(new GpioDoorbellListener(this.doorbellService));
        }
        else {
            LOGGER.error("Failed to resolve configured WiringPi-GPIO pin '{}' with description '{}'.", address, description);
        }
    }
}
