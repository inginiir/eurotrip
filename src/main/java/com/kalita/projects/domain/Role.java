package com.kalita.projects.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    USER, ADMIN;


    @Override
    public String getAuthority() {
        return name();
    }
}
