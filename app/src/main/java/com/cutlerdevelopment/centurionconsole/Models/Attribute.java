package com.cutlerdevelopment.centurionconsole.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.cutlerdevelopment.centurionconsole.Helpers.AttributeHelper;
import com.cutlerdevelopment.centurionconsole.Integrations.FirestoreHandler;

import org.w3c.dom.Attr;

import java.util.ArrayList;

public class Attribute implements Parcelable {

    private int type;
    private int value;

    public Attribute(int type, int value) {
        this.type = type;
        this.value = value;
    }

    private Attribute(Parcel in) {
        type = in.readInt();
        value = in.readInt();
    }


    public int getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public void reduceValue() {
        if (this.value == AttributeHelper.NEW_CENTURION_MIN_SCORE) {
            return;
        }
        this.value--;
    }
    public void increaseValue() {
        this.value++;
    }

    public static final Creator<Attribute> CREATOR = new Creator<Attribute>() {
        @Override
        public Attribute createFromParcel(Parcel in) {
            return new Attribute(in);
        }

        @Override
        public Attribute[] newArray(int size) {
            return new Attribute[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(type);
        parcel.writeInt(value);
    }
}
