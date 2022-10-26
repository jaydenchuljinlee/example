package com.sdi.alarm.service;

import com.sdi.alarm.dto.DeviceDto;
import com.sdi.alarm.dto.RequestMessage;
import com.sdi.alarm.dto.ResponseMessage;
import com.sdi.alarm.entity.DeviceInformation;
import com.sdi.alarm.repository.DeviceInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AlarmServiceImpl implements AlarmService{
    // TODO kafka에 적재해야 한다.
    private final DeviceInformationRepository repository;

    @Override
    public ResponseMessage send(RequestMessage requestMessage) {


        return null;
    }

    @Cacheable(key = "#id", value = "DeviceDto")
    @Override
    public DeviceDto getMessage(String id) {
        DeviceInformation entity = repository.findById(id);

        return DeviceDto.from(entity);
    }


}
