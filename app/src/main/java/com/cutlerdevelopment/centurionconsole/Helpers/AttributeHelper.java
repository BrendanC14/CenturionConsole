package com.cutlerdevelopment.centurionconsole.Helpers;

import android.content.res.Resources;

import com.cutlerdevelopment.centurionconsole.Constants.AttributeTypes;
import com.cutlerdevelopment.centurionconsole.Models.Attribute;
import com.cutlerdevelopment.centurionconsole.R;

import java.util.ArrayList;
import java.util.List;

public class AttributeHelper {

    private static final String STRENGTH_SHORT_STRING = "STR";
    private static final String STRENGTH_LONG_STRING = "Strength";
    private static final String DEXTERITY_SHORT_STRING = "DEX";
    private static final String DEXTERITY_LONG_STRING = "Dexterity";
    private static final String CONSTITUTION_SHORT_STRING = "CON";
    private static final String CONSTITUTION_LONG_STRING = "Constitution";
    private static final String INTELLIGENCE_SHORT_STRING = "INT";
    private static final String INTELLIGENCE_LONG_STRING = "Intelligence";
    private static final String WISDOM_SHORT_STRING = "WIS";
    private static final String WISDOM_LONG_STRING = "Wisdom";
    private static final String CHARISMA_SHORT_STRING = "CHA";
    private static final String CHARISMA_LONG_STRING = "Charisma";

    private static final String ATHLETICS_SHORT_STRING = "ATH";
    private static final String ATHLETICS_LONG_STRING = "Athletics";
    private static final String ACROBATICS_SHORT_STRING = "ACR";
    private static final String ACROBATICS_LONG_STRING = "Acrobatics";
    private static final String SLEIGHT_OF_HAND_SHORT_STRING = "SoH";
    private static final String SLEIGHT_OF_HAND_LONG_STRING = "Sleight of Hand";
    private static final String STEALTH_SHORT_STRING = "STH";
    private static final String STEALTH_LONG_STRING = "Stealth";
    private static final String HISTORY_SHORT_STRING = "HIS";
    private static final String HISTORY_LONG_STRING = "History";
    private static final String INVESTIGATION_SHORT_STRING = "INV";
    private static final String INVESTIGATION_LONG_STRING = "Investigation";
    private static final String NATURE_SHORT_STRING = "NAT";
    private static final String NATURE_LONG_STRING = "Nature";
    private static final String RELIGION_SHORT_STRING = "REL";
    private static final String RELIGION_LONG_STRING = "Religion";
    private static final String ANIMAL_HANDLING_SHORT_STRING = "AHA";
    private static final String ANIMAL_HANDLING_LONG_STRING = "Animal Handling";
    private static final String INSIGHT_SHORT_STRING = "INS";
    private static final String INSIGHT_LONG_STRING = "Insight";
    private static final String MEDICINE_SHORT_STRING = "MED";
    private static final String MEDICINE_LONG_STRING = "Medicine";
    private static final String PERCEPTION_SHORT_STRING = "PRC";
    private static final String PERCEPTION_LONG_STRING = "Perception";
    private static final String SURVIVAL_SHORT_STRING = "SRV";
    private static final String SURVIVAL_LONG_STRING = "Survival";
    private static final String DECEPTION_SHORT_STRING = "DEC";
    private static final String DECEPTION_LONG_STRING = "Deception";
    private static final String INTIMIDATION_SHORT_STRING = "ITM";
    private static final String INTIMIDATION_LONG_STRING = "Intimidation";
    private static final String PERFORMANCE_SHORT_STRING = "PRF";
    private static final String PERFORMANCE_LONG_STRING = "Performance";
    private static final String PERSUASION_SHORT_STRING = "PRS";
    private static final String PERSUASION_LONG_STRING = "Persuasion";
    private static final String INITIATIVE_SHORT_STRING = "INI";
    private static final String INITIATIVE_LONG_STRING = "Initiative";

    public static final int NEW_CENTURION_MAX_SCORE = 10;
    public static final int NEW_CENTURION_MIN_SCORE = -10;

    public static String getAttributeName(int type, boolean longName) {
        switch (type) {
            case AttributeTypes.STRENGTH : return longName ? STRENGTH_LONG_STRING : STRENGTH_SHORT_STRING;
            case AttributeTypes.DEXTERITY : return longName ? DEXTERITY_LONG_STRING : DEXTERITY_SHORT_STRING;
            case AttributeTypes.CONSTITUTION : return longName ? CONSTITUTION_LONG_STRING : CONSTITUTION_SHORT_STRING;
            case AttributeTypes.INTELLIGENCE : return longName ? INTELLIGENCE_LONG_STRING : INTELLIGENCE_SHORT_STRING;
            case AttributeTypes.WISDOM : return longName ? WISDOM_LONG_STRING : WISDOM_SHORT_STRING;
            case AttributeTypes.CHARISMA : return longName ? CHARISMA_LONG_STRING : CHARISMA_SHORT_STRING;
            case AttributeTypes.ATHLETICS : return longName ? ATHLETICS_LONG_STRING : ATHLETICS_SHORT_STRING;
            case AttributeTypes.ACROBATICS : return longName ? ACROBATICS_LONG_STRING : ACROBATICS_SHORT_STRING;
            case AttributeTypes.SLEIGHT_OF_HAND : return longName ? SLEIGHT_OF_HAND_LONG_STRING : SLEIGHT_OF_HAND_SHORT_STRING;
            case AttributeTypes.STEALTH : return longName ? STEALTH_LONG_STRING : STEALTH_SHORT_STRING;
            case AttributeTypes.HISTORY : return longName ? HISTORY_LONG_STRING : HISTORY_SHORT_STRING;
            case AttributeTypes.INVESTIGATION : return longName ? INVESTIGATION_LONG_STRING : INVESTIGATION_SHORT_STRING;
            case AttributeTypes.NATURE : return longName ? NATURE_LONG_STRING : NATURE_SHORT_STRING;
            case AttributeTypes.RELIGION : return longName ? RELIGION_LONG_STRING : RELIGION_SHORT_STRING;
            case AttributeTypes.ANIMAL_HANDLING : return longName ? ANIMAL_HANDLING_LONG_STRING : ANIMAL_HANDLING_SHORT_STRING;
            case AttributeTypes.INSIGHT : return longName ? INSIGHT_LONG_STRING : INSIGHT_SHORT_STRING;
            case AttributeTypes.MEDICINE : return longName ? MEDICINE_LONG_STRING : MEDICINE_SHORT_STRING;
            case AttributeTypes.PERCEPTION : return longName ? PERCEPTION_LONG_STRING : PERCEPTION_SHORT_STRING;
            case AttributeTypes.SURVIVAL : return longName ? SURVIVAL_LONG_STRING : SURVIVAL_SHORT_STRING;
            case AttributeTypes.DECEPTION : return longName ? DECEPTION_LONG_STRING : DECEPTION_SHORT_STRING;
            case AttributeTypes.INTIMIDATION : return longName ? INTIMIDATION_LONG_STRING : INTIMIDATION_SHORT_STRING;
            case AttributeTypes.PERFORMANCE : return longName ? PERFORMANCE_LONG_STRING : PERFORMANCE_SHORT_STRING;
            case AttributeTypes.PERSUASION : return longName ? PERSUASION_LONG_STRING : PERSUASION_SHORT_STRING;
            case AttributeTypes.INITIATIVE : return longName ? INITIATIVE_LONG_STRING : INITIATIVE_SHORT_STRING;
            default: return "Default";
        }
    }

    public static int getAttributeType(String name) {
        switch (name) {
            case STRENGTH_LONG_STRING : case STRENGTH_SHORT_STRING : return AttributeTypes.STRENGTH;
            case DEXTERITY_LONG_STRING : case DEXTERITY_SHORT_STRING : return AttributeTypes.DEXTERITY;
            case CONSTITUTION_LONG_STRING : case CONSTITUTION_SHORT_STRING : return AttributeTypes.CONSTITUTION;
            case INTELLIGENCE_LONG_STRING : case INTELLIGENCE_SHORT_STRING : return AttributeTypes.INTELLIGENCE;
            case WISDOM_LONG_STRING : case WISDOM_SHORT_STRING : return AttributeTypes.WISDOM;
            case CHARISMA_LONG_STRING : case CHARISMA_SHORT_STRING : return AttributeTypes.CHARISMA;
            case ATHLETICS_LONG_STRING : case ATHLETICS_SHORT_STRING : return AttributeTypes.ATHLETICS;
            case ACROBATICS_LONG_STRING : case ACROBATICS_SHORT_STRING : return AttributeTypes.ACROBATICS;
            case SLEIGHT_OF_HAND_LONG_STRING : case SLEIGHT_OF_HAND_SHORT_STRING : return AttributeTypes.SLEIGHT_OF_HAND;
            case STEALTH_LONG_STRING : case STEALTH_SHORT_STRING : return AttributeTypes.STEALTH;
            case HISTORY_LONG_STRING : case HISTORY_SHORT_STRING : return AttributeTypes.HISTORY;
            case INVESTIGATION_LONG_STRING : case INVESTIGATION_SHORT_STRING : return AttributeTypes.INVESTIGATION;
            case NATURE_LONG_STRING : case NATURE_SHORT_STRING : return AttributeTypes.NATURE;
            case RELIGION_LONG_STRING : case RELIGION_SHORT_STRING : return AttributeTypes.RELIGION;
            case ANIMAL_HANDLING_LONG_STRING : case ANIMAL_HANDLING_SHORT_STRING : return AttributeTypes.ANIMAL_HANDLING;
            case INSIGHT_LONG_STRING : case INSIGHT_SHORT_STRING : return AttributeTypes.INSIGHT;
            case MEDICINE_LONG_STRING : case MEDICINE_SHORT_STRING : return AttributeTypes.MEDICINE;
            case PERCEPTION_LONG_STRING : case PERCEPTION_SHORT_STRING : return AttributeTypes.PERCEPTION;
            case SURVIVAL_LONG_STRING : case SURVIVAL_SHORT_STRING : return AttributeTypes.SURVIVAL;
            case DECEPTION_LONG_STRING : case DECEPTION_SHORT_STRING : return AttributeTypes.DECEPTION;
            case INTIMIDATION_LONG_STRING : case INTIMIDATION_SHORT_STRING : return AttributeTypes.INTIMIDATION;
            case PERFORMANCE_LONG_STRING : case PERFORMANCE_SHORT_STRING : return AttributeTypes.PERFORMANCE;
            case PERSUASION_LONG_STRING : case PERSUASION_SHORT_STRING : return AttributeTypes.PERSUASION;
            case INITIATIVE_LONG_STRING : case INITIATIVE_SHORT_STRING : return AttributeTypes.INITIATIVE;
            default: return 1;
        }
    }

    public static List<Integer> getAllBaseAttributes() {
        List<Integer> list = new ArrayList<>();
        list.add(AttributeTypes.STRENGTH);
        list.add(AttributeTypes.DEXTERITY);
        list.add(AttributeTypes.INTELLIGENCE);
        list.add(AttributeTypes.WISDOM);
        list.add(AttributeTypes.CHARISMA);
        list.add(AttributeTypes.CONSTITUTION);
        return list;
    }

    public static int getAttributeLevel(int value) {
        return value + 10;
    }

    public static ArrayList<Attribute> getEmptyBaseAttributes() {
        ArrayList<Attribute> attributes = new ArrayList<>();
        for (int type : getAllBaseAttributes()) {
            attributes.add(new Attribute(type, 0));
        }
        return attributes;
    }

    public static ArrayList<Integer> getAttributesChildrenTypes(String parentName) {
        return getAttributesChildrenTypes(getAttributeType(parentName));
    }

    public static ArrayList<Integer> getAttributesChildrenTypes(int parentType) {
        ArrayList<Integer> children = new ArrayList<>();
        switch (parentType) {
            case AttributeTypes.STRENGTH :
                children.add(AttributeTypes.ATHLETICS);
                break;
            case AttributeTypes.DEXTERITY:
                children.add(AttributeTypes.ACROBATICS);
                children.add(AttributeTypes.SLEIGHT_OF_HAND);
                children.add(AttributeTypes.STEALTH);
                children.add(AttributeTypes.INITIATIVE);
                break;
            case AttributeTypes.INTELLIGENCE:
                children.add(AttributeTypes.HISTORY);
                children.add(AttributeTypes.INVESTIGATION);
                children.add(AttributeTypes.NATURE);
                children.add(AttributeTypes.RELIGION);
                break;
            case AttributeTypes.WISDOM:
                children.add(AttributeTypes.ANIMAL_HANDLING);
                children.add(AttributeTypes.INSIGHT);
                children.add(AttributeTypes.MEDICINE);
                children.add(AttributeTypes.PERCEPTION);
                children.add(AttributeTypes.SURVIVAL);
                break;
            case AttributeTypes.CHARISMA:
                children.add(AttributeTypes.DECEPTION);
                children.add(AttributeTypes.INTIMIDATION);
                children.add(AttributeTypes.PERFORMANCE);
                children.add(AttributeTypes.PERSUASION);
                break;
        }
        return children;
    }
}
