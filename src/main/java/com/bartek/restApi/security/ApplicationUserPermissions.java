package com.bartek.restApi.security;

public enum ApplicationUserPermissions {
    USER_CREATE("USER:CREATE"),
    USER_READ("USER:READ"),
    USER_DELTE("USER:DELTE"),
    USER_UPDATE("USER:UPDATE");

    private final String permission;

    ApplicationUserPermissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}

