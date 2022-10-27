package com.sdi.alarm.service;

import com.sdi.alarm.dto.DeviceDto;

public interface DeviceService {
    DeviceDto getDeviceInfo(String id);
}
