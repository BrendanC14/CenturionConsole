package com.cutlerdevelopment.centurionconsole.Data;

import android.provider.ContactsContract;

import com.cutlerdevelopment.centurionconsole.Integrations.FirestoreHandler;
import com.cutlerdevelopment.centurionconsole.Interfaces.FirestoreListener;
import com.cutlerdevelopment.centurionconsole.Models.Centurion;
import com.cutlerdevelopment.centurionconsole.Models.PersonalityModifier;

import java.util.ArrayList;
import java.util.List;

public class DataHolder implements FirestoreListener {
    private static DataHolder instance;
    public static DataHolder getInstance() {
        return instance == null ? new DataHolder() : instance;
    }
    private DataHolder() {
        centurions = new ArrayList<>();
        FirestoreHandler.getInstance().addAListener(this);
        instance = this;
    }

    private List<Centurion> centurions;
    public List<Centurion> getCenturions() {
        return centurions;
    }

    private ArrayList<PersonalityModifier> personalityModifiers;
    public ArrayList<PersonalityModifier> getPersonalityModifiers() { return personalityModifiers; }

    public PersonalityModifier getPersonalityModifier(String name) {
        for (PersonalityModifier modifier : personalityModifiers) {
            if (modifier.getName().equals(name)) {
                return modifier;
            }
        }
        return null;
    }

    @Override
    public void centurionAdded(Centurion centurion) {
        this.centurions.add(centurion);
    }

    @Override
    public void gotAllCenturions(List<Centurion> centurions) {
        this.centurions = centurions;
    }

    @Override
    public void gotAllPersonalityModifiers(ArrayList<PersonalityModifier> personalityModifiers) {
        this.personalityModifiers = personalityModifiers;
    }
}
