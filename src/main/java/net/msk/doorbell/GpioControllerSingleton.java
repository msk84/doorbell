package net.msk.doorbell;

import com.pi4j.io.gpio.*;
import com.pi4j.util.CommandArgumentParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GpioControllerSingleton {

    private static final Logger LOGGER = LoggerFactory.getLogger(GpioControllerSingleton.class);

    private final GpioController gpio;

    public GpioControllerSingleton() {
        this.gpio = GpioFactory.getInstance();
        this.initializePins();
    }

    private void initializePins() {
        GpioPinDigitalInput doorbellInput = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05,"doorbellInput", PinPullResistance.PULL_DOWN);
        doorbellInput.addListener(new GpioDoorbellListener());
    }
}
