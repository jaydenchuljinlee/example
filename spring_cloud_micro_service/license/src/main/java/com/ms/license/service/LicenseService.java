package com.ms.license.service;

import com.ms.license.client.OrganizationRestTemplateClient;
import com.ms.license.model.License;
import com.ms.license.model.Organization;
import com.ms.license.repository.LicenseRepository;
import com.ms.license.utils.UserContextHolder;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class LicenseService {
    private static final Logger logger = LoggerFactory.getLogger(LicenseService.class);
    private static final String ORGANIZATION_INSTANCE = "organization";
    private static final String LICENSE_INSTANCE = "license";

    private final LicenseRepository repository;
    private final OrganizationRestTemplateClient organizationRestClient;

    @CircuitBreaker(name = ORGANIZATION_INSTANCE, fallbackMethod = "getOrganizationSupportFallBack")
    public Organization getOrganization(String organizationId) {
        return organizationRestClient.getOrganization(organizationId);
    }

    private List<License> getOrganizationSupportFallBack(Throwable t) {
        logger.debug("==> getOrganizationSupportFallBack: " + t.getClass());

        return new ArrayList<>();
    }

    private void randomlyRunLong() {
        Random rand = new Random();

        int randomNum = rand.nextInt((3 - 1) + 1) + 1;

        if (randomNum == 3) sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(11000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    @CircuitBreaker(name = LICENSE_INSTANCE, fallbackMethod = "getOrganizationSupportFallBack")
    public List<License> getLicenseByOrg(String organizationId) {
        logger.debug("LicenseService.getLicensesByOrg  Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        randomlyRunLong();

        return repository.findByOrganizationId(organizationId);
    }
}
