package com.sdi.alarm.dto;

import com.sdi.alarm.entity.DeviceInformation;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DeviceDto {
    private String id;
    private String osType;
    private String phone;

    private DeviceDto(DeviceInformation entity) {
        this.id = entity.getId();
        this.osType = entity.getOsType();
        this.phone = entity.getPhone();
    }

    public static DeviceDto from(DeviceInformation entity) {
        return new DeviceDto(entity);
    }
}
