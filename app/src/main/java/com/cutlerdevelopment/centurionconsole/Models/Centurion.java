package com.cutlerdevelopment.centurionconsole.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.cutlerdevelopment.centurionconsole.Constants.FirestoreKeys;
import com.cutlerdevelopment.centurionconsole.Helpers.AttributeHelper;
import com.cutlerdevelopment.centurionconsole.Integrations.FirestoreHandler;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Centurion implements Parcelable {

    private String name;
    private int age;
    private String occupation;
    private String birthplace;
    private String bio;
    private ArrayList<Attribute> attributes;
    private ArrayList<String> personalityModifiers;

    public Centurion(String name,
                     int age,
                     String occupation,
                     String birthplace,
                     String bio,
                     ArrayList<Attribute> attributes,
                     ArrayList<String> personalityModifiers,
                     Boolean loading) {
        this.name = name;
        this.age = age;
        this.occupation = occupation;
        this.birthplace = birthplace;
        this.bio = bio;
        this.attributes = attributes;
        this.personalityModifiers = personalityModifiers;
        if (!loading) {
            FirestoreHandler.getInstance().saveCenturion(this);
        }
    }

    protected Centurion(Parcel in) {
        name = in.readString();
        age = in.readInt();
        occupation = in.readString();
        birthplace = in.readString();
        bio = in.readString();
        this.attributes = new ArrayList<>();
        in.readTypedList(attributes, Attribute.CREATOR);
        this.personalityModifiers = new ArrayList<>();
        in.readStringList(personalityModifiers);
    }

    public String getName() {
        return name;
    }
    public int getAge() { return age; }
    public String getOccupation() { return occupation; }
    public String getBirthplace() { return birthplace; }
    public String getBio() {
        return bio;
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public ArrayList<Attribute> getChildAttributesOfType(Attribute parent) {
        ArrayList<Attribute> childAttributes = new ArrayList<>();
        for (int childType : AttributeHelper.getAttributesChildrenTypes(parent.getType())) {
            childAttributes.add(new Attribute(childType, parent.getValue()));
        }
        return childAttributes;
    }

    public ArrayList<String> getPersonalityModifiers() {
        return personalityModifiers;
    }

    public Map<String, Object> convertToMap() {
        Map<String, Object> dataMap = new HashMap<>();

        Map<String, Integer> attributeMap = new HashMap<>();
        dataMap.put(FirestoreKeys.CENTURION_NAME_KEY, this.name);
        dataMap.put(FirestoreKeys.CENTURION_AGE_KEY, this.age);
        dataMap.put(FirestoreKeys.CENTURION_OCCUPATION_KEY, this.occupation);
        dataMap.put(FirestoreKeys.CENTURION_BIRTHPLACE_KEY, this.birthplace);
        dataMap.put(FirestoreKeys.CENTURION_BIO_KEY, this.bio);
        for (Attribute attribute : this.attributes) {
            attributeMap.put(AttributeHelper.getAttributeName(attribute.getType(), true), attribute.getValue());
        }
        dataMap.put(FirestoreKeys.CENTURION_ATTRIBUTES_KEY, attributeMap);
        dataMap.put(FirestoreKeys.CENTURION_PERSONALITIES_KEY, personalityModifiers);
        return dataMap;
    }

    public static final Creator<Centurion> CREATOR = new Creator<Centurion>() {
        @Override
        public Centurion createFromParcel(Parcel in) {
            return new Centurion(in);
        }

        @Override
        public Centurion[] newArray(int size) {
            return new Centurion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(age);
        parcel.writeString(occupation);
        parcel.writeString(birthplace);
        parcel.writeString(bio);
        parcel.writeTypedList(attributes);
        parcel.writeStringList(personalityModifiers);
    }
}
