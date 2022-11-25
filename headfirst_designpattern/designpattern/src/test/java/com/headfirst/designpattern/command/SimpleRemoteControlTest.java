package com.headfirst.designpattern.command;

import com.headfirst.designpattern.command.simpleremote.Light;
import com.headfirst.designpattern.command.simpleremote.LightOnCommand;
import com.headfirst.designpattern.command.simpleremote.SimpleRemoteControl;
import org.junit.jupiter.api.Test;

public class SimpleRemoteControlTest {
    @Test
    public void 간단한_테스트() {
        SimpleRemoteControl remote = new SimpleRemoteControl();

        Light light = new Light("living room ");
        LightOnCommand lightOn = new LightOnCommand(light);

        remote.setCommand(lightOn);
        remote.buttonWasPressed();
    }
}
