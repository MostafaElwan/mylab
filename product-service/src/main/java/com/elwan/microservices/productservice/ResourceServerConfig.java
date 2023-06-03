package com.elwan.microservices.productservice;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class ResourceServerConfig {
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		
		return httpSecurity
		        .csrf(csrf -> csrf.disable())
		        .authorizeHttpRequests(auth -> auth
		            .requestMatchers("/actuator/**", "/").permitAll()
		            .anyRequest().authenticated()
		        )
		        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		        .oauth2ResourceServer(oauth2 -> oauth2
		                .jwt(jwt -> jwt
		                    .jwtAuthenticationConverter(jwtAuthenticationConverter())
		                ))
		        .httpBasic(Customizer.withDefaults()).build();
		        
    }
	

	private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
		JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
		jwtConverter.setJwtGrantedAuthoritiesConverter(new Converter<Jwt, Collection<GrantedAuthority>>() {

			@Override
		    public Collection<GrantedAuthority> convert(Jwt jwt) {
		        final Map<String, List<String>> resourceAccess = (Map<String, List<String>>) jwt.getClaims().get("resource_access");
		        Map<String, List<String>> clientAccess = (Map<String, List<String>>)resourceAccess.get("demo-service");
		        return  clientAccess
		        		.get("roles")
		        		.stream()
		                .map(SimpleGrantedAuthority::new)
		                .collect(Collectors.toList());
		    }
			
		});
		return jwtConverter;
	}
}


