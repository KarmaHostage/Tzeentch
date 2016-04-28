package com.karmahostage.tzeentch.core.adb.command;

public enum KeyEvent {
    BACK("4");

    private String eventString;

    KeyEvent(String eventString) {
        this.eventString = eventString;
    }

    public String getEventString() {
        return eventString;
    }
}
