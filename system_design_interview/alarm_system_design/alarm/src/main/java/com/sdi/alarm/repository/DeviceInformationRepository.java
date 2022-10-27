package com.sdi.alarm.repository;

import com.sdi.alarm.entity.DeviceInformation;
import com.sdi.alarm.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceInformationRepository extends JpaRepository<DeviceInformation, String> {
    Optional<DeviceInformation> findById(String id);
}
