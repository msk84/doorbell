package net.msk.doorbell.eventSources;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;
import net.msk.doorbell.DoorbellEvent;
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

    @Value("${doorbell.pin.debounce_delay_millis:1000}")
    private long DOORBELL_DEBOUNCE_DELAY_TIME_MILLIS;

    private final Context pi4j = Pi4J.newAutoContext();
    private final DoorbellService doorbellService;

    public GpioControllerSingleton(final DoorbellService doorbellService) {
        LOGGER.trace("Created bean 'GpioControllerSingleton'.");
        this.doorbellService = doorbellService;
    }

    @Override
    public void afterPropertiesSet() {
        this.initializePins();
    }

    private void initializePins() {
        LOGGER.trace("Configured properties:: Doorbell_1_PinWiringpi_address: {},  DoorbellPinDebounce_delay_millis {}",
                DOORBELL_1_INPUT_PIN_ADDRESS, DOORBELL_DEBOUNCE_DELAY_TIME_MILLIS);

        if(DOORBELL_1_INPUT_PIN_ADDRESS != null) {
            var buttonConfig = DigitalInput.newConfigBuilder(this.pi4j)
                    .id("bell")
                    .name("DoorBell")
                    .address(DOORBELL_1_INPUT_PIN_ADDRESS)
                    .pull(PullResistance.PULL_DOWN)
                    .debounce(DOORBELL_DEBOUNCE_DELAY_TIME_MILLIS)
                    .provider("pigpio-digital-input");
            final DigitalInput button = pi4j.create(buttonConfig);
            button.addListener(e -> {
                if(e.state() == DigitalState.LOW) {
                    LOGGER.info("Button was pressed! -- LOW");
                }
                else {
                    LOGGER.info("Button was pressed! -- HIGH");
                    LOGGER.info("Ringing the bell!");
                    DoorbellEvent event = new DoorbellEvent("doorbell.ring", "Doorbell rings.");
                    this.doorbellService.processEvent(event);
                }
            });

            LOGGER.trace("Registered doorbell 1 for pin {}", DOORBELL_1_INPUT_PIN_ADDRESS);
        }
    }
}
