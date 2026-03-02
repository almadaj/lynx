package com.schoolar.lynx.domain.enums;

public enum LanguageLevel {
    A1("Iniciante"),
    A2("Básico"),
    B1("Intermediário"),
    B2("Intermediário Avançado"),
    C1("Avançado"),
    C2("Proficiente");

    private final String description;

    LanguageLevel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
