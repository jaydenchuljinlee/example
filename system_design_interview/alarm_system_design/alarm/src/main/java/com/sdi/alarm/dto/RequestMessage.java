package com.sdi.alarm.dto;

import com.sdi.alarm.b_enum.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor @Getter @Setter
public class RequestMessage {
    private MessageType type;
    private String contents;
    private List<String> targetList;
}
