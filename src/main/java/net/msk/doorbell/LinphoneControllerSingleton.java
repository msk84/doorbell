package net.msk.doorbell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.concurrent.TimeUnit;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class LinphoneControllerSingleton {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinphoneControllerSingleton.class);

    @Value("${voip.number2call}")
    private String VOIP_NUMBER_TO_CALL;

    LinphoneControllerSingleton() {
        LOGGER.trace("Created bean 'LinphoneControllerSingleton'.");
    }

    public void doCall() {
        try {
            LOGGER.trace("Do call");
            final Process proc = Runtime.getRuntime().exec("linphonec -s " + VOIP_NUMBER_TO_CALL);
            //BufferedReader linphoneOutput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            // Kill process after 5 seconds
            if(!proc.waitFor(5, TimeUnit.SECONDS)) {
                proc.destroy();
            }

            LOGGER.trace("Hangup");
        }
        catch (final IOException | InterruptedException e) {
            LOGGER.error("Exception while doing linphone call.", e);
        }
    }
}
