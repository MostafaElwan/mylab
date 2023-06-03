package com.elwan.microservices.currencyconversionservice.security.fiegn;

import java.util.Objects;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class OAuth2Provider {

  // Using anonymous user principal as its S2S authentication
  public static final Authentication ANONYMOUS_USER_AUTHENTICATION =
      new AnonymousAuthenticationToken(
          "key", "anonymous", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));

  private final OAuth2AuthorizedClientManager authorizedClientManager;

  public String getAuthenticationToken(final String authZServerName) {
    final OAuth2AuthorizeRequest request =
        OAuth2AuthorizeRequest.withClientRegistrationId(authZServerName)
            .principal(ANONYMOUS_USER_AUTHENTICATION)
            .build();
    
    OAuth2AuthorizedClient authorizedClient = this.authorizedClientManager.authorize(request);
    OAuth2AccessToken accessToken = Objects.requireNonNull(authorizedClient).getAccessToken();
  
    log.info("Issued: " + accessToken.getIssuedAt().toString() + ", Expires:" + accessToken.getExpiresAt().toString());
    log.info("Scopes: " + accessToken.getScopes().toString());
    log.info("Token: " + accessToken.getTokenValue());
	
    return "Bearer " + authorizedClientManager.authorize(request).getAccessToken().getTokenValue();
  }

  
}
