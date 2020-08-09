package com.cutlerdevelopment.centurionconsole.Interfaces;

import com.cutlerdevelopment.centurionconsole.Models.Centurion;
import com.cutlerdevelopment.centurionconsole.Models.PersonalityModifier;

import java.util.ArrayList;
import java.util.List;

public interface FirestoreListener {

    void centurionAdded(Centurion centurion);
    void gotAllCenturions(List<Centurion> centurions);
    void gotAllPersonalityModifiers(ArrayList<PersonalityModifier> personalityModifiers);
}
