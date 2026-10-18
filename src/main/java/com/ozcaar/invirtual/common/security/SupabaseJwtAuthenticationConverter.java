package com.ozcaar.invirtual.common.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.ozcaar.invirtual.user.model.UserModel;
import com.ozcaar.invirtual.user.service.UserProvisioningService;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SupabaseJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

  private final UserProvisioningService users;

  public SupabaseJwtAuthenticationConverter(UserProvisioningService users) {
    this.users = users;
  }

  @SuppressWarnings("null")
  @Override
  public AbstractAuthenticationToken convert(Jwt jwt) {

    String supabaseId = jwt.getSubject();

    String email = jwt.getClaimAsString("email");
    if (email == null || email.isBlank()) {
      Map<String, Object> userMeta = jwt.getClaim("user_metadata");
      if (userMeta != null) {
        Object e = userMeta.get("email");
        if (e instanceof String s)
          email = s;
      }
    }

    Object userMetadata = jwt.getClaim("user_metadata");
    UserModel user = users.getOrCreate(supabaseId, email, userMetadata);

    // Convert roles -> GrantedAuthorities ("ROLE_DEV", etc.)
    Collection<GrantedAuthority> authorities = toAuthorities(user.getUser_roles().stream()
        .map(ur -> ur.getRole().getName())
        .collect(Collectors.toSet()));

    // principalName by default = subject
    String principalName = (email != null && !email.isBlank()) ? email : supabaseId;

    return new JwtAuthenticationToken(jwt, authorities, principalName);
  }

  private Collection<GrantedAuthority> toAuthorities(Set<String> roles) {
    if (roles == null || roles.isEmpty())
      return List.of(new SimpleGrantedAuthority("ROLE_USER"));

    return roles.stream()
        .filter(Objects::nonNull)
        .map(String::trim)
        .filter(s -> !s.isBlank())
        .map(s -> s.startsWith("ROLE_") ? s : "ROLE_" + s)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toUnmodifiableList());
  }
}
