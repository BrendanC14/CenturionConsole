package com.cutlerdevelopment.centurionconsole.Integrations;

import android.util.Pair;

import androidx.annotation.NonNull;

import com.cutlerdevelopment.centurionconsole.Constants.AttributeTypes;
import com.cutlerdevelopment.centurionconsole.Constants.FirestoreKeys;
import com.cutlerdevelopment.centurionconsole.Data.DataHolder;
import com.cutlerdevelopment.centurionconsole.Helpers.AttributeHelper;
import com.cutlerdevelopment.centurionconsole.Interfaces.FirestoreListener;
import com.cutlerdevelopment.centurionconsole.Models.Attribute;
import com.cutlerdevelopment.centurionconsole.Models.Centurion;
import com.cutlerdevelopment.centurionconsole.Models.PersonalityModifier;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreHandler {

    private static FirestoreHandler instance = null;
    public static FirestoreHandler getInstance() {
        return instance == null ? new FirestoreHandler() : instance;
    }
    private FirestoreHandler() {
        listeners = new ArrayList<>();
        instance = this;
    }
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<FirestoreListener> listeners;
    public void addAListener(FirestoreListener listener) {
        this.listeners.add(listener);
    }

    public void saveCenturion(final Centurion centurion) {
        db.collection(FirestoreKeys.CENTURIONS_COLLECTION_NAME).document(centurion.getName())
                .set(centurion.convertToMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        centurionSaved(centurion);
                    }
                });
    }

    private void centurionSaved(Centurion centurion) {
        for (FirestoreListener listener : listeners) { listener.centurionAdded(centurion); }
    }

    /**
     * Calls firestore for all Centurions. Uses the listener to say when it's done. Can be null if don't care.
     * @param listener
     */
    public void getAllCenturions(FirestoreListener listener) {
        if (listener != null) { listeners.add(listener); }
        db.collection(FirestoreKeys.CENTURIONS_COLLECTION_NAME).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                           centurionsRetrieved(task);
                        }
                    }
                });
    }


    private void centurionsRetrieved(Task<QuerySnapshot> task) {
        List<Centurion> centurions = new ArrayList<>();
        for (QueryDocumentSnapshot doc : task.getResult()) {
            ArrayList<Attribute> attributes = new ArrayList<>();
            Map<?,?> attributesReturned = (Map<?,?>) doc.get(FirestoreKeys.CENTURION_ATTRIBUTES_KEY);
            for (Map.Entry<String, Integer> pair : validateFirestoreMap(attributesReturned).entrySet()) {
                attributes.add(new Attribute(
                        AttributeHelper.getAttributeType(pair.getKey()),
                        pair.getValue()
                ));
            }
            ArrayList<String> modifierNames;
            modifierNames = (ArrayList<String>) doc.get(FirestoreKeys.CENTURION_PERSONALITIES_KEY);
            centurions.add(new Centurion(
                    doc.getString(FirestoreKeys.CENTURION_NAME_KEY),
                    doc.getLong(FirestoreKeys.CENTURION_AGE_KEY).intValue(),
                    doc.getString(FirestoreKeys.CENTURION_OCCUPATION_KEY),
                    doc.getString(FirestoreKeys.CENTURION_BIRTHPLACE_KEY),
                    doc.getString(FirestoreKeys.CENTURION_BIO_KEY),
                    attributes,
                    modifierNames,
                    true
            ));
        }
        for (FirestoreListener listener : listeners) { listener.gotAllCenturions(centurions); }
    }

    public void getAllPersonalityModifiers(FirestoreListener listener) {
        if (listener != null) { listeners.add(listener); }
        db.collection(FirestoreKeys.PERSONALITY_COLLECTION_NAME).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            personalityModifiersRetrieved(task);
                        }
                    }
                });
    }

    private void personalityModifiersRetrieved(Task<QuerySnapshot> task) {
        ArrayList<PersonalityModifier> personalityModifiers = new ArrayList<>();
        for (QueryDocumentSnapshot doc : task.getResult()) {
            HashMap<String, Integer> modifierMap = new HashMap<>();
            Map<?,?> modifiersReturned = (Map<?,?>) doc.get(FirestoreKeys.PERSONALITY_MODIFIERS_KEY);
            for (Map.Entry<String, Integer> pair : validateFirestoreMap(modifiersReturned).entrySet()) {
                modifierMap.put(pair.getKey(), pair.getValue());
            }
            personalityModifiers.add(new PersonalityModifier(
                    doc.getString(FirestoreKeys.PERSONALITY_NAME_KEY),
                    doc.getString(FirestoreKeys.PERSONALITY_DESC_KEY),
                    doc.getLong(FirestoreKeys.PERSONALITY_COST_KEY).intValue(),
                    modifierMap
            ));
        }
        for(FirestoreListener listener : listeners) { listener.gotAllPersonalityModifiers(personalityModifiers); }
    }

    private Map<String, Integer> validateFirestoreMap(Map<?,?> mapReceived) {
        Map<String, Integer> map = new HashMap<>();
        if (mapReceived == null) {
            return map;
        }

        for (Map.Entry<?,?> pair : mapReceived.entrySet()) {
            if (pair.getKey() instanceof  String && pair.getValue() instanceof Long) {
                String key = (String) pair.getKey();
                int value = ((Long) pair.getValue()).intValue();
                map.put(key, value);
            }
        }
        return map;

    }
}