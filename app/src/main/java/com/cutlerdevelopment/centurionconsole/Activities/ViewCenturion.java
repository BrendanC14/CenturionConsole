package com.cutlerdevelopment.centurionconsole.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutlerdevelopment.centurionconsole.Data.DataHolder;
import com.cutlerdevelopment.centurionconsole.Helpers.AttributeHelper;
import com.cutlerdevelopment.centurionconsole.Helpers.PersonalityImpactDisplay;
import com.cutlerdevelopment.centurionconsole.Models.Attribute;
import com.cutlerdevelopment.centurionconsole.Models.Centurion;
import com.cutlerdevelopment.centurionconsole.Models.PersonalityModifier;
import com.cutlerdevelopment.centurionconsole.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewCenturion extends AppCompatActivity {

    private LinearLayout attributeLayout, personalityLayout;
    private Centurion centurion;
    private List<PersonalityModifier> modifiers;

    private HashMap<String, List<PersonalityImpactDisplay>> attributeModifierMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_centurion);

        centurion  = getIntent().getParcelableExtra("Centurion");
        TextView nameText = findViewById(R.id.viewCenturionName);
        Button bioButton = findViewById(R.id.viewCenturionBioButton);
        attributeLayout = findViewById(R.id.viewCenturionAttributesGrid);
        personalityLayout = findViewById(R.id.viewCenturionPersonalityLayout);
        nameText.setText(centurion.getName());
        bioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewBio();
            }
        });

        modifiers = new ArrayList<>();

        for (String modifierName : centurion.getPersonalityModifiers()) {
            PersonalityModifier modifier = DataHolder.getInstance().getPersonalityModifier(modifierName);
            personalityLayout.addView(addModifierView(modifier));
            modifiers.add(modifier);
        }
        attributeModifierMap = populateAttributeModifierMap();
        for (Attribute attribute : centurion.getAttributes()) {
            attributeLayout.addView(addAttributeView(attribute));
        }

        personalityLayout.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_right));

    }

    private View addAttributeView(final Attribute attribute) {
        View attributeView = getLayoutInflater().inflate(R.layout.view_base_attribute_modifiers, null);
        TextView attributeNameText = attributeView.findViewById(R.id.attributeModifierName);
        TextView attributeLevel = attributeView.findViewById(R.id.attributeModifierLevel);
        Button attributeValue = attributeView.findViewById(R.id.attributeModifierValue);
        LinearLayout childAttributeLayout = attributeView.findViewById(R.id.attributeModifierGrid);

        String attributeName = AttributeHelper.getAttributeName(attribute.getType(), true);
        attributeNameText.setText(attributeName);
        attributeLevel.setText(String.valueOf(AttributeHelper.getAttributeLevel(attribute.getValue())));
        int value = attribute.getValue() + getModifierImpactToAttribute(attributeName);

        if ( value > 0) {
            attributeValue.setText("+" + value);
            attributeValue.setTextColor(this.getResources().getColor(R.color.colorGreen));
        }
        else if ( value < 0) {
            attributeValue.setText(String.valueOf(value));
            attributeValue.setTextColor(this.getResources().getColor(R.color.colorRed));
        }
        else {
            attributeValue.setText("0");
            attributeValue.setTextColor(this.getResources().getColor(R.color.colorWhite));
        }
        attributeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAttributeDialog(attribute);
            }
        });
        for (Attribute childAttribute : centurion.getChildAttributesOfType(attribute)) {
            childAttributeLayout.addView(addChildAttributeView(childAttribute));
        }
        attributeView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_top));
        return attributeView;
    }

    private View addChildAttributeView(final Attribute attribute) {
        View attributeView = getLayoutInflater().inflate(R.layout.view_child_attribute_modifiers, null);
        TextView attributeNameText = attributeView.findViewById(R.id.childAttributeModifierName);
        Button attributeValue = attributeView.findViewById(R.id.childAttributeModifierValue);

        String attributeName = AttributeHelper.getAttributeName(attribute.getType(), true);
        attributeNameText.setText(attributeName);
        int value = attribute.getValue() + getModifierImpactToAttribute(attributeName);
        if ( value > 0) {
            attributeValue.setText("+" + value);
            attributeValue.setTextColor(this.getResources().getColor(R.color.colorGreen));
        }
        else if ( value < 0) {
            attributeValue.setText(String.valueOf(value));
            attributeValue.setTextColor(this.getResources().getColor(R.color.colorRed));
        }
        else {
            attributeValue.setText("0");
            attributeValue.setTextColor(this.getResources().getColor(R.color.colorWhite));
        }
        attributeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAttributeDialog(attribute);
            }
        });
        return attributeView;
    }

    private void viewBio() {
        new AlertDialog.Builder(this)
                .setTitle("Bio")
                .setMessage(centurion.getBio())
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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
                openModifierDialog(modifier);
            }
        });
        return view;
    }

    private int getModifierImpactToAttribute(String attributeName) {
        int totalImpact = 0;
        if (attributeModifierMap.containsKey(attributeName)) {
            List<PersonalityImpactDisplay> impacts = attributeModifierMap.get(attributeName);
            for (PersonalityImpactDisplay impact : impacts) {
                totalImpact += impact.getModifier();
            }
        }
        return totalImpact;
    }

    private HashMap<String, List<PersonalityImpactDisplay>> populateAttributeModifierMap() {
        HashMap<String, List<PersonalityImpactDisplay>> map = new HashMap<>();
        for(PersonalityModifier modifier : modifiers) {
            for (Map.Entry<String, Integer> pair : modifier.getModifierMap().entrySet()) {
                if (!map.containsKey(pair.getKey())) {
                    map.put(pair.getKey(), new ArrayList<PersonalityImpactDisplay>());
                }
                map.get(pair.getKey()).add(new PersonalityImpactDisplay(
                        modifier.getName(),
                        pair.getValue()
                ));
                ArrayList<Integer> children = AttributeHelper.getAttributesChildrenTypes(pair.getKey());
                for (int childType : children) {
                    String childName = AttributeHelper.getAttributeName(childType, true);
                    if (!map.containsKey(childName)) {
                        map.put(childName, new ArrayList<PersonalityImpactDisplay>());
                    }
                    map.get(childName).add(new PersonalityImpactDisplay(
                            modifier.getName(),
                            pair.getValue()
                    ));
                }


            }
        }
        return map;
    }

    private void openAttributeDialog(Attribute attribute) {
        new AlertDialog.Builder(this)
                .setTitle(AttributeHelper.getAttributeName(attribute.getType(), true))
                .setMessage(getAttributeDialogDisplay(attribute))
                .setPositiveButton("OK", null)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    private String getAttributeDialogDisplay(Attribute attribute) {
        String dialogDisplay = "";
        String attributeName = AttributeHelper.getAttributeName(attribute.getType(), true);

        dialogDisplay += "This Modifier has been influenced by the following: \n";
        int baseValue = attribute.getValue();
        if (baseValue > 0) {
            dialogDisplay += "+" + baseValue + " for Level " + AttributeHelper.getAttributeLevel(baseValue) + "\n";
        }
        else if (baseValue < 0) {
            dialogDisplay += baseValue + " for Level " + AttributeHelper.getAttributeLevel(baseValue) + "\n";
        }

        if (attributeModifierMap.containsKey(attributeName)) {
            for (PersonalityImpactDisplay display : attributeModifierMap.get(attributeName)) {
                if (display.getModifier() > 0) {
                    dialogDisplay += "+" + display.getModifier() + " for " + display.getName() + " personality.\n";
                }
                else {
                    dialogDisplay += display.getModifier() + " for " + display.getName() + " personality.\n";
                }
            }
        }
        return dialogDisplay;
    }

    private void openModifierDialog(final PersonalityModifier modifier) {
        new AlertDialog.Builder(this)
                .setTitle(modifier.getName())
                .setMessage(modifier.getModifierDialogDescription())
                .setPositiveButton("OK", null)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }
}
