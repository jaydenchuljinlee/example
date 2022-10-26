package com.sdi.alarm.entity;

import com.sdi.alarm.b_enum.MessageType;
import lombok.Getter;

import javax.persistence.*;

@Entity @Getter
public class Message {
    @Id
    private String id;

    @Column(name = "sender")
    private String sender;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @Column(name = "contents")
    private String contents;

    @Column(name = "app_version")
    private String appVersion;
}
