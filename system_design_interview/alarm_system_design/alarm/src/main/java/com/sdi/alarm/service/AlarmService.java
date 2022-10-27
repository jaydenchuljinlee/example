package com.sdi.alarm.service;

import com.sdi.alarm.dto.DeviceDto;
import com.sdi.alarm.dto.RequestMessage;
import com.sdi.alarm.dto.ResponseMessage;

public interface AlarmService {
    ResponseMessage send(RequestMessage requestMessage);

}
