package com.ms.license.client;

import com.ms.license.model.Organization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class OrganizationRestTemplateClient {
    private RestTemplate restTemplate;

    public Organization getOrganization(String organizationId) {
        ResponseEntity<Organization> restExchange = restTemplate.exchange(
                "http://organization/v1/organization/{organizationId}",
                HttpMethod.GET,
                null, Organization.class, organizationId
        );

        return restExchange.getBody();
    }
}
