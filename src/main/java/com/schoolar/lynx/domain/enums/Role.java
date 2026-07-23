package com.schoolar.lynx.domain.enums;

public enum Role {

    STUDENT(1),
    TEACHER(2),
    HEADTEACHER(3),
    ADMIN(4);

    private final int level;

    Role(int level) {
        this.level = level;
    }

    public boolean hasPermission(Role required) {
        return this.level >= required.level;
    }
}