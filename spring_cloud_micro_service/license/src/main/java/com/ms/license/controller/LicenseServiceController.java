package com.ms.license.controller;

import com.ms.license.model.License;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "v1/organizations/{organizationId}/licenses")
@RestController
public class LicenseServiceController {
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
