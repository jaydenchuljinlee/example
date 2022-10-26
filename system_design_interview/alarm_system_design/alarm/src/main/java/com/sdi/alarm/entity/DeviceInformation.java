package com.sdi.alarm.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity @Getter
public class DeviceInformation {
    @Id
    private String id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "os_type")
    private String osType;
}
