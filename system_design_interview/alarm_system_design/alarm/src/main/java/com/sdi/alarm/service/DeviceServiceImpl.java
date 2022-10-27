package com.sdi.alarm.service;

import com.sdi.alarm.dto.DeviceDto;
import com.sdi.alarm.entity.DeviceInformation;
import com.sdi.alarm.repository.DeviceInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DeviceServiceImpl implements DeviceService{
    private final DeviceInformationRepository repository;

    @Cacheable(key = "#id", value = "DeviceDto")
    @Override
    public DeviceDto getDeviceInfo(String id) {

        Optional<DeviceInformation> optional = repository.findById(id);

        if (optional.isEmpty()) return null;

        DeviceInformation entity = repository.findById(id).get();

        return DeviceDto.from(entity);
    }
}
