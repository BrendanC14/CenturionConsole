package com.cutlerdevelopment.centurionconsole.Helpers;

public class PersonalityImpactDisplay {

    private String name;
    private int modifier;

    public PersonalityImpactDisplay(String name, int modifier) {
        this.name = name;
        this.modifier = modifier;
    }

    public String getName() {
        return name;
    }

    public int getModifier() {
        return modifier;
    }
}
