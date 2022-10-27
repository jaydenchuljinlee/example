package com.sdi.alarm.service;

import com.sdi.alarm.dto.DeviceDto;
import com.sdi.alarm.dto.RequestMessage;
import com.sdi.alarm.dto.ResponseMessage;
import com.sdi.alarm.entity.DeviceInformation;
import com.sdi.alarm.repository.DeviceInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AlarmServiceImpl implements AlarmService{
    // TODO kafka에 적재해야 한다.
    private final DeviceService deviceService;

    @Override
    public ResponseMessage send(RequestMessage requestMessage) {

        List<String> targetList = requestMessage.getTargetList();

        for (String target: targetList) {
            DeviceDto deviceInfo = deviceService.getDeviceInfo(target);
        }



        return null;
    }




}
