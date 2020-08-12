package com.cutlerdevelopment.centurionconsole.Models;

import java.util.HashMap;
import java.util.Map;

public class PersonalityModifier {

    private String name;
    private String description;
    private int cost;
    private HashMap<String, Integer> modifierMap;

    public PersonalityModifier(String name, String description, int cost, HashMap<String, Integer> modifierMap) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.modifierMap = modifierMap;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    public HashMap<String, Integer> getModifierMap() {
        return modifierMap;
    }


    public String getModifierDialogDescription() {
        String textToDisplay;
        textToDisplay = name + "\n" +
                "Personality Cost: " + cost + "\n" +
                description + "\n" +
                "Modifiers: \n";
        for (Map.Entry<String, Integer> pair : modifierMap.entrySet()) {
            textToDisplay += pair.getKey() + ": " +
                    (pair.getValue() > 0
                    ? "+" + pair.getValue()
                    : pair.getValue())
                    + "\n";
        }
        return textToDisplay;
    }
}
