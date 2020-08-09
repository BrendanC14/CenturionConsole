package com.cutlerdevelopment.centurionconsole.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.cutlerdevelopment.centurionconsole.Data.DataHolder;
import com.cutlerdevelopment.centurionconsole.Integrations.FirestoreHandler;
import com.cutlerdevelopment.centurionconsole.Interfaces.FirestoreListener;
import com.cutlerdevelopment.centurionconsole.Models.Attribute;
import com.cutlerdevelopment.centurionconsole.Models.Centurion;
import com.cutlerdevelopment.centurionconsole.Models.PersonalityModifier;
import com.cutlerdevelopment.centurionconsole.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FirestoreListener {

    private Button centurionButton;
    private TextView loadingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        centurionButton = findViewById(R.id.centurionsButton);
        loadingText = findViewById(R.id.loadingText);
        DataHolder.getInstance();

        centurionButton.setVisibility(View.GONE);
        loadingText.setText("Loading Centurions...");

        FirestoreHandler.getInstance().getAllCenturions(this);

    }

    public void openCenturions(View view) {
        Intent intent = new Intent(this, CenturionsActivity.class);
        startActivity(intent);
    }

    @Override
    public void centurionAdded(Centurion centurion) {

    }

    @Override
    public void gotAllCenturions(List<Centurion> centurions) {
        loadingText.setText("Loading Personality Modifiers...");
        FirestoreHandler.getInstance().getAllPersonalityModifiers(this);

    }

    @Override
    public void gotAllPersonalityModifiers(ArrayList<PersonalityModifier> personalityModifiers) {
        centurionButton.setVisibility(View.VISIBLE);
        centurionButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        loadingText.setVisibility(View.GONE);
        loadingText.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));

    }
}
