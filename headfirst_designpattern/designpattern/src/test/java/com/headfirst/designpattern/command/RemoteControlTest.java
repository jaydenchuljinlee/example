package com.headfirst.designpattern.command;

import com.headfirst.designpattern.command.remote.*;
import org.junit.jupiter.api.Test;

public class RemoteControlTest {
    @Test
    public void 리모콘_테스트() {
        RemoteControl rc = new RemoteControl();

        Light livingRoomLight = new Light("Living Room");
        Stereo stereo = new Stereo("Living Room");

        LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand livingRoomLightOff= new LightOffCommand(livingRoomLight);

        StereoOnWithCDCommand stereoOnWithCD = new StereoOnWithCDCommand(stereo);
        StereoOffCommand stereoOff = new StereoOffCommand(stereo);

        rc.setCommands(0, livingRoomLightOn, livingRoomLightOff);
        rc.setCommands(1, stereoOnWithCD, stereoOff);

        rc.onButtonWasPushed(0);
        rc.offButtonWasPushed(0);
        rc.onButtonWasPushed(1);
        rc.offButtonWasPushed(1);
    }

    @Test
    public void 리모콘_undo_테스트() {
        RemoteControlWithUndo rc = new RemoteControlWithUndo();

        Light livingRoomLight = new Light("Living Room");
        Stereo stereo = new Stereo("Living Room");

        LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand livingRoomLightOff= new LightOffCommand(livingRoomLight);

        rc.setCommands(0, livingRoomLightOn, livingRoomLightOff);

        rc.onButtonWasPushed(0);
        rc.offButtonWasPushed(0);
        System.out.println(rc);
        rc.undoButtonWasPushed();

        rc.onButtonWasPushed(0);
        rc.offButtonWasPushed(0);
        System.out.println(rc);
        rc.undoButtonWasPushed();
    }
}
