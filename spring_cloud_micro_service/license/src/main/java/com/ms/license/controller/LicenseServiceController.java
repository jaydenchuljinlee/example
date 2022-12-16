package com.ms.license.controller;

import com.ms.license.model.License;
import com.ms.license.service.LicenseService;
import com.ms.license.utils.UserContextHolder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "v1/organizations/{organizationId}/licenses")
@RestController
public class LicenseServiceController {
    private static final Logger logger = LoggerFactory.getLogger(LicenseServiceController.class);

    private final LicenseService service;

    @GetMapping(value = "/{licenseId}")
    public License getLicense(
            @PathVariable("organizationId") String organizationId
            ,@PathVariable("licenseId") String licenseId) {
        return new License()
                .withId(licenseId)
                .withOrganizationId(organizationId)
                .withProductName("Teleco")
                .withLicenseType("Seat");
    }

    @GetMapping("/")
    public List<License> getLicense(@PathVariable("organizationId") String organizationId) {
        //logger.debug("LicenseServiceController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        return service.getLicenseByOrg(organizationId);
    }

    @PutMapping(value = "{licenseId}")
    public String updateLicenses(@PathVariable("licenseId") String licenseId) {
        return "This is the put";
    }

    @PostMapping(value = "{licenseId}")
    public String saveLicenses(@PathVariable("licenseId") String licenseId) {
        return "This is the post";
    }

    @DeleteMapping(value = "{licenseId}")
    public String deleteLicenses(@PathVariable("licenseId") String licenseId) {
        return "This is the delete";
    }
}
