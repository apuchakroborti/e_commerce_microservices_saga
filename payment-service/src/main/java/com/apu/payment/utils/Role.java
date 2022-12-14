package com.apu.payment.utils;

import java.util.HashMap;
import java.util.Map;

public enum Role {
    ADMIN("ADMIN"),
    USER("USER"),
    EMPLOYEE("EMPLOYEE");
    private final String role;

    Role(String role) {
        this.role = role;
    }

    private final static Map<String, Role> lookup = new HashMap<>();

    static {
        for(Role userRole: values()) {
            lookup.put(userRole.getValue(), userRole);
        }
    }

    public String getValue() {
        return this.role;
    }

    public static Role get(String role) {
        return lookup.get(role);
    }
}
