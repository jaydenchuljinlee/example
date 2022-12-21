package com.ms.organization.service;

import com.ms.organization.config.KafkaProducer;
import com.ms.organization.domain.Organization;
import com.ms.organization.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrganizationService {
    private final OrganizationRepository repository;
    private final KafkaProducer producer;

    public Organization getOrg(String organizationId) {
        Optional<Organization> organization = repository.findById(organizationId);

        if (organization.isEmpty()) {
            throw new NullPointerException("organizationId-" + organizationId);
        }
        return organization.get();
    }

    public void saveOrg(Organization org) {
        org.setId(UUID.randomUUID().toString());

        repository.save(org);

        producer.send(org);
    }

    public void updateOrg(Organization org) {
        repository.save(org);
    }

    public void deleteOrg(Organization org) {
        repository.delete(org);
    }
}
