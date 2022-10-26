package com.sdi.alarm.b_enum;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MessageParams {
    TO("to"), FROM("from"), TYPE("type"), TEXT("text"), APP_VERSION("app_version");

    private String value;
}
