package com.ms.gateway.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.result.view.RequestContext;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class FilterUtils {
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTH_TOKEN     = "tmx-auth-token";
    public static final String USER_ID        = "tmx-user-id";
    public static final String ORG_ID         = "tmx-org-id";
    public static final String PRE_FILTER_TYPE = "pre";
    public static final String POST_FILTER_TYPE = "post";
    public static final String ROUTE_FILTER_TYPE = "route";

    private final ServerHttpRequest request;
    private final ServerHttpResponse response;

    public String getCorrelationId(){
        return getPropertyValueBy(CORRELATION_ID);
    }

    public void setCorrelationId(String correlationId){
        setPropertyValueBy(CORRELATION_ID, correlationId);
    }

    public  final String getOrgId(){
        return getPropertyValueBy(ORG_ID);
    }

    public void setOrgId(String orgId){
        setPropertyValueBy(ORG_ID,  orgId);
    }

    public final String getUserId(){
        return getPropertyValueBy(USER_ID);
    }

    public void setUserId(String userId){
        setPropertyValueBy(USER_ID,  userId);
    }

    public final String getAuthToken(){
        return getPropertyValueBy(AUTH_TOKEN);
    }

    public String getServiceId(){
        return getPropertyValueBy("serviceId");
    }

    private void setPropertyValueBy(String key, String value) {
        request.getHeaders().add(key, value);
    }

    private String getPropertyValueBy(String key) {
        return Objects.requireNonNull(request.getHeaders().get(key)).toString();
    }
}
