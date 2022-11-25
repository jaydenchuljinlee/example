package com.headfirst.designpattern.command.remote;

public class StereoOnWithCDCommand implements Command{
    Stereo stereo;

    public StereoOnWithCDCommand(Stereo stereo) {
        this.stereo = stereo;
    }

    @Override
    public void execute() {
        stereo.on();
        stereo.setCD();
        stereo.setVolume(1);
    }

    @Override
    public void undo() {
        stereo.off();
    }
}
