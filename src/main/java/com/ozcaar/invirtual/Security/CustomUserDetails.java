package com.ozcaar.invirtual.Security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ozcaar.invirtual.Models.UserModel;

public class CustomUserDetails implements UserDetails {
    private final UserModel user;

    public CustomUserDetails(UserModel user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return (user.getUser_roles() == null ? List.<GrantedAuthority>of() :
        user.getUser_roles().stream()
        .filter(ur -> ur.getActive() != null && ur.getActive())
        .map(userRole -> "ROLE_" + userRole.getRole().getName())
        .map(SimpleGrantedAuthority::new)
        .toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword_hash();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return user.getActive(); }
}
