package com.elwan.microservices.currencyconversionservice.security.fiegn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BarAuthZConfiguration {

  private final OAuth2Provider oauth2Provider;
  static final String AUTHZ_SERVER_NAME = "keycloak-spring-gateway-client";
  
  @Bean
  public RequestInterceptor barAuthZInterceptor() {
    return (requestTemplate) ->
        requestTemplate.header(
            HttpHeaders.AUTHORIZATION, oauth2Provider.getAuthenticationToken(AUTHZ_SERVER_NAME));
  }
}
