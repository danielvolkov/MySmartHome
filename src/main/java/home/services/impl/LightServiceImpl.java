package home.services.impl;

import com.pi4j.io.gpio.*;
import home.services.LightService;
import home.services.holders.LightGroupHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author danielvolkov94@gmail.com
 */
@Service
public class LightServiceImpl implements LightService {

    @Autowired
    LightGroupHolder lightGroupHolder;

    @Override
    public void turnLight(String lightGroup) {
        Pin raspiPin = lightGroupHolder.getPin(lightGroup);
        final GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(
                raspiPin, lightGroup, PinState.LOW);
        pulse(pin,gpio);
    }

    private void pulse(GpioPinDigitalOutput pin, GpioController gpio) {
        pin.setShutdownOptions(true, PinState.LOW);
        pin.low();
        try {
            Thread.sleep(200);
        }catch ( InterruptedException e){ }
        pin.high();
        //gpio.shutdown();
        gpio.unprovisionPin(pin);
    }
}
