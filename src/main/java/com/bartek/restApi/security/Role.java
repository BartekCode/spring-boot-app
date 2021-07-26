package com.bartek.restApi.security;

import org.springframework.security.core.GrantedAuthority;
import org.thymeleaf.expression.Sets;

import java.util.HashSet;
import java.util.Set;

public enum Role  implements GrantedAuthority {
    ADMIN("ADMIN", new HashSet<>()), USER("USER",new HashSet<>());

    private final String role;
    private final Set <ApplicationUserPermissions> permissions;

    Role(String role, Set<ApplicationUserPermissions> permissions) {
        this.role = role;
        this.permissions = permissions;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    public Set<ApplicationUserPermissions> getPermissions() {
        return permissions;
    }
}

