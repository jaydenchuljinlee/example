package com.ms.organization.controller;

import com.ms.organization.domain.Organization;
import com.ms.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(value = "v1/organization")
@RestController
public class OrganizationController {
    private final OrganizationService service;

    @GetMapping(value="/{organizationId}")
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        return service.getOrg(organizationId);
    }

    @PutMapping(value="/{organizationId}")
    public void updateOrganization(@PathVariable("organizationId") String orgId, @RequestBody Organization org) {
        service.updateOrg( org );
    }

    @PostMapping
    public void saveOrganization(@RequestBody Organization org) {
        service.saveOrg( org );
    }

    @DeleteMapping(value="/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization( @PathVariable("orgId") String orgId,  @RequestBody Organization org) {
        service.deleteOrg( org );
    }
}
