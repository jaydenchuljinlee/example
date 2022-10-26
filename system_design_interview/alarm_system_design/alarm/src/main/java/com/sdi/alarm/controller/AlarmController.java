package com.sdi.alarm.controller;

import com.sdi.alarm.dto.RequestMessage;
import com.sdi.alarm.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/alarm")
@RestController
public class AlarmController {
    private AlarmService alarmService;

    @PostMapping
    public ResponseEntity<RequestMessage> send(@RequestBody RequestMessage requestMessage) {



        return ResponseEntity
                .ok()
                .build();
    }
}
