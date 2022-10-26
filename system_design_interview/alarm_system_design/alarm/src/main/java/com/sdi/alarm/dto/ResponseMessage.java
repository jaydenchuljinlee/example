package com.sdi.alarm.dto;

import com.sdi.alarm.b_enum.MessageType;
import com.sdi.alarm.entity.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ResponseMessage {
    private String id;
    private MessageType messageType;
    private String receiver;
    private String contents;

    private ResponseMessage(Message entity) {
        this.id = entity.getId();
        this.messageType = entity.getType();
        this.receiver = entity.getReceiver();
        this.contents = entity.getContents();
    }

    public static ResponseMessage from(Message entity) {
        return new ResponseMessage(entity);
    }
}
