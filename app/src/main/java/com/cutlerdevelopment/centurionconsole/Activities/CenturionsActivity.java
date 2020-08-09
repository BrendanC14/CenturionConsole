package com.cutlerdevelopment.centurionconsole.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cutlerdevelopment.centurionconsole.Data.DataHolder;
import com.cutlerdevelopment.centurionconsole.Integrations.FirestoreHandler;
import com.cutlerdevelopment.centurionconsole.Interfaces.FirestoreListener;
import com.cutlerdevelopment.centurionconsole.Models.Centurion;
import com.cutlerdevelopment.centurionconsole.Models.PersonalityModifier;
import com.cutlerdevelopment.centurionconsole.R;

import java.util.ArrayList;
import java.util.List;

public class CenturionsActivity extends AppCompatActivity implements FirestoreListener {

    private LinearLayout centurionsButtonsParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centurions);
        centurionsButtonsParent =  findViewById(R.id.activityCenturionsButtonsParent);
        for (Centurion centurion : DataHolder.getInstance().getCenturions()) {
            addCenturionButton(centurion);
        }
    }

    void addCenturionButton(final Centurion centurion) {
        Button centurionButton = (Button) getLayoutInflater().inflate(R.layout.button_centurion, null);
        centurionsButtonsParent.addView(centurionButton);
        centurionButton.setText(centurion.getName());
        centurionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewCenturion(centurion);
            }
        });
        centurionButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_left));
    }

    public void addNewCenturion(View view) {
        Intent intent = new Intent(this, AddCenturion.class);
        startActivity(intent);
    }

    public void viewCenturion(Centurion centurion) {
        Intent intent = new Intent(this, ViewCenturion.class);
        intent.putExtra("Centurion", centurion);
        startActivity(intent);

    }

    @Override
    public void centurionAdded(Centurion centurion) {
        addCenturionButton(centurion);
    }

    @Override
    public void gotAllCenturions(List<Centurion> centurions) {

    }

    @Override
    public void gotAllPersonalityModifiers(ArrayList<PersonalityModifier> personalityModifiers) {

    }
}
