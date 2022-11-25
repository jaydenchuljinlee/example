package com.headfirst.designpattern.command.remote;

import org.springframework.lang.Nullable;

public interface Command {
    public void execute();
    public void undo();
}
