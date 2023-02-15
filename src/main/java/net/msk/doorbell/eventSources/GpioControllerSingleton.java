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

    @Value("${doorbell.1.pin.bcm_address}")
    private Integer DOORBELL_1_INPUT_PIN_ADDRESS;

    @Value("${doorbell.pin.debounce_delay_millis:100000}")
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
        LOGGER.trace("Configured properties:: Doorbell_1_PinBcm_address: {},  DoorbellPinDebounce_delay_millis {}",
                this.DOORBELL_1_INPUT_PIN_ADDRESS, this.DOORBELL_DEBOUNCE_DELAY_TIME_MILLIS);

        if (this.DOORBELL_1_INPUT_PIN_ADDRESS != null) {
            var buttonConfig = DigitalInput.newConfigBuilder(this.pi4j)
                    .id("bell")
                    .name("DoorBell")
                    .address(this.DOORBELL_1_INPUT_PIN_ADDRESS)
                    .pull(PullResistance.PULL_DOWN)
                    .debounce(this.DOORBELL_DEBOUNCE_DELAY_TIME_MILLIS)
                    .provider("pigpio-digital-input");
            final DigitalInput button = this.pi4j.create(buttonConfig);
            button.addListener(e -> {
                if (e.state() == DigitalState.LOW) {
                    LOGGER.trace("BmcPin {} back to LOW", this.DOORBELL_1_INPUT_PIN_ADDRESS);
                }
                else {
                    LOGGER.trace("BmcPin {} back to HIGH", this.DOORBELL_1_INPUT_PIN_ADDRESS);
                    LOGGER.info("Ringing the bell!");
                    final DoorbellEvent event = new DoorbellEvent("doorbell.ring", "Doorbell rings.");
                    this.doorbellService.processEvent(event);
                }
            });
        }
    }
}
