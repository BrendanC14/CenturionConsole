package com.cutlerdevelopment.centurionconsole.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutlerdevelopment.centurionconsole.DialogFragments.PersonalityModifiersFragment;
import com.cutlerdevelopment.centurionconsole.Helpers.AttributeHelper;
import com.cutlerdevelopment.centurionconsole.Models.Centurion;
import com.cutlerdevelopment.centurionconsole.Models.PersonalityModifier;
import com.cutlerdevelopment.centurionconsole.R;
import com.cutlerdevelopment.centurionconsole.ViewAdapters.EditAttributeAdapter;
import com.cutlerdevelopment.centurionconsole.ViewAdapters.PersonalitySummarayAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;

public class AddCenturion extends AppCompatActivity implements EditAttributeAdapter.EditAttributeListener, PersonalitySummarayAdapter.PersonalityListener {

    private TextInputEditText nameText, ageText, occupationText, birthplaceText, bioText;
    private TextView pointsText, personalityCostText;
    private GridView attributeGrid;
    private LinearLayout personalityModifierLayout;
    private ConstraintLayout part1Layout, part2Layout;
    private Button backButton, nextButton, saveButton;

    private EditAttributeAdapter adapter;

    private int attributePoints;
    private int personalityCosts;
    private ArrayList<PersonalityModifier> modifiersSelected;
    private HashMap<PersonalityModifier, View> modifierViewMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_centurion);
        nameText = findViewById(R.id.addCenturionNameText);
        ageText = findViewById(R.id.addCenturionAgeText);
        occupationText = findViewById(R.id.addCenturionOccupationText);
        birthplaceText = findViewById(R.id.addCenturionBirthplaceText);
        bioText = findViewById(R.id.addCenturionBioText);
        pointsText = findViewById(R.id.addCenturionPointsAvailable);
        personalityCostText = findViewById(R.id.addCenturionPersonalityCosts);
        attributeGrid = findViewById(R.id.addCenturionAttributesGrid);
        personalityModifierLayout = findViewById(R.id.addCenturionPersonalityLayout);
        part1Layout = findViewById(R.id.addCenturionPart1Layout);
        part2Layout = findViewById(R.id.addCenturionPart2Layout);
        backButton = findViewById(R.id.addCenturionBackButton);
        nextButton = findViewById(R.id.addCenturionNextButton);
        saveButton = findViewById(R.id.addCenturionSaveButton);

        pointsText.setText("0 points remaining.");
        pointsText.setTextColor(this.getResources().getColor(R.color.colorRed));

        ArrayList<String> allAttributes = new ArrayList<>();
        for (int type : AttributeHelper.getAllBaseAttributes()) {
            allAttributes.add(AttributeHelper.getAttributeName(type, true));
        }
        adapter = new EditAttributeAdapter(this, AttributeHelper.getEmptyBaseAttributes(), this);
        attributeGrid.setAdapter(adapter);
        attributeGrid.setNumColumns(3);

        personalityCosts = 0;
        updatePersonalityCostText();
        modifiersSelected = new ArrayList<>();
        modifierViewMap = new HashMap<>();

    }

    public void attributeHelp(View view) {

        new AlertDialog.Builder(this)
                .setTitle("Attributes")
                .setMessage("Your attributes will influence your performance. For example, if Brendan asks you to perform a Strength check, " +
                        "you have to roll a d20 and beat the number needed for success. \n" +
                        "Each attribute starts at 10. For every point above 10, you can add that number of points to your dice roll. \n" +
                        "For every point below 10 you reduce the number from your dice roll.\n" +
                        "A level 15 in Strength will give you a +5 to every Strength roll.\n" +
                        "You have 60 points to spend across all the attributes.\n" +
                        "A score can't go below 0 and, for a new character, can't go above 20.")

                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    public void personalityHelp(View view) {

        new AlertDialog.Builder(this)
                .setTitle("Personality Modifiers")
                .setMessage("Your personality is what makes your character you. Here you have the opportunity to choose specific modifiers. \n" +
                        "One purpose of the Personality Modifiers are to help roleplay, using these as a reminder to how your character would respond. \n" +
                        "The Modifiers also provide attribute benefits/penalties.\n" +
                        "Each Modifier has a Cost, before you can create a new Centurion, the cost of your Modifiers has to = 0. \n" +
                        "Whilst it would be good to just have positive Personality Modifiers, you must supplement those with negatives!")

                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    public void openPersonalityModifiers(View view) {
        PersonalityModifiersFragment modifierFragment = new PersonalityModifiersFragment(this);
        modifierFragment.show(getSupportFragmentManager(), "ModifierFragment");
    }

    @Override
    public void attributeEdited(int points) {
        attributeGrid.invalidateViews();
        String pointsString = points == 1
                ? points + " point remaining."
                : points + " points remaining";
        pointsText.setText(pointsString);
        int textColor = points == 0 ? R.color.colorRed : R.color.colorGreen;
        pointsText.setTextColor(this.getResources().getColor(textColor));
        attributePoints = points;
        checkSaveButtonVisibility();
    }

    @Override
    public void personalityOpened(PersonalityModifier modifier) {
        if (modifiersSelected.contains(modifier)) {
            openModifierToRemove(modifier);
        } else {
            openModifierToAdd(modifier);
        }
    }

    private void openModifierToAdd(final PersonalityModifier modifier) {
        new AlertDialog.Builder(this)
                .setTitle(modifier.getName())
                .setMessage(modifier.getModifierDialogDescription())
                .setPositiveButton("Add Modifier", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        addModifierToCenturion(modifier);
                    }
                })
                .setNegativeButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    private void openModifierToRemove(final PersonalityModifier modifier) {
        new AlertDialog.Builder(this)
                .setTitle(modifier.getName())
                .setMessage(modifier.getModifierDialogDescription())
                .setPositiveButton("Remove Modifier", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        removeModifierFromCenturion(modifier);
                    }
                })
                .setNegativeButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();

    }


    private void addModifierToCenturion(PersonalityModifier modifier) {
        modifiersSelected.add(modifier);
        View modifierView = addModifierView(modifier);
        personalityModifierLayout.addView(modifierView);
        modifierViewMap.put(modifier, modifierView);
        personalityCosts += modifier.getCost();
        updatePersonalityCostText();
        checkSaveButtonVisibility();
    }

    private void removeModifierFromCenturion(PersonalityModifier modifier) {
        modifiersSelected.remove(modifier);
        View modifierView = modifierViewMap.get(modifier);
        personalityModifierLayout.removeView(modifierView);
        modifierViewMap.remove(modifier);
        personalityCosts -= modifier.getCost();
        updatePersonalityCostText();
        checkSaveButtonVisibility();
    }

    private View addModifierView(final PersonalityModifier modifier) {
        View view = getLayoutInflater().inflate(R.layout.view_personality_summary, null);
        Button nameText = view.findViewById(R.id.personalitySummaryName);
        nameText.setText(modifier.getName());
        if (modifier.getCost() < 0) {
            nameText.setTextColor(this.getResources().getColor(R.color.colorRed));
        }
        else {
            nameText.setTextColor(this.getResources().getColor(R.color.colorGreen));
        }
        nameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openModifierToRemove(modifier);
            }
        });
        return view;
    }

    private void updatePersonalityCostText() {
        personalityCostText.setText("Personality Costs: " + personalityCosts);
        if (personalityCosts == 0) {
            personalityCostText.setTextColor(this.getResources().getColor(R.color.colorGreen));
        } else {
            personalityCostText.setTextColor(this.getResources().getColor(R.color.colorRed));
        }
    }

    private void checkSaveButtonVisibility() {
        if (attributePoints == 0 && personalityCosts == 0) {
            saveButton.setVisibility(View.VISIBLE);
        } else {
            saveButton.setVisibility(View.INVISIBLE);
        }
    }

    public void saveCenturion(View view) {
        String name = nameText.getText().toString();
        if ((name.equals(""))) {
            nameText.setHint("Choose a name");
            return;
        }
        int age = Integer.parseInt(ageText.getText().toString());
        String occupation = occupationText.getText().toString();
        String birthplace = birthplaceText.getText().toString();
        String bio = bioText.getText().toString();
        ArrayList<String> modifierNames = new ArrayList<>();
        for (PersonalityModifier modifier : modifiersSelected) {
            modifierNames.add(modifier.getName());
        }
        new Centurion(name, age, occupation, birthplace, bio, adapter.getAttributes(), modifierNames, false);
        finish();
    }

    public void backPressed(View view) {
        part1Layout.setVisibility(View.VISIBLE);
        part2Layout.setVisibility(View.GONE);
        Animation.AnimationListener listener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                nextButton.setVisibility(View.VISIBLE);
                backButton.setVisibility(View.GONE);
                part1Layout.clearAnimation();
                part2Layout.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };

        Animation slideOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
        Animation slideInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        slideOutAnim.setAnimationListener(listener);
        slideInAnim.setAnimationListener(listener);
        part1Layout.startAnimation(slideInAnim);
        part2Layout.startAnimation(slideOutAnim);
    }
    public void nextPressed(View view) {
        part1Layout.setVisibility(View.GONE);
        part2Layout.setVisibility(View.VISIBLE);

        Animation.AnimationListener listener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                nextButton.setVisibility(View.GONE);
                backButton.setVisibility(View.VISIBLE);
                part1Layout.clearAnimation();
                part2Layout.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };

        Animation slideOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        Animation slideInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        slideOutAnim.setAnimationListener(listener);
        slideInAnim.setAnimationListener(listener);
        part1Layout.startAnimation(slideOutAnim);
        part2Layout.startAnimation(slideInAnim);


    }
}

