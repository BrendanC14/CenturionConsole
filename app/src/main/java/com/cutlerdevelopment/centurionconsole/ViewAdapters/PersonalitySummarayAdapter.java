package com.cutlerdevelopment.centurionconsole.ViewAdapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cutlerdevelopment.centurionconsole.Models.PersonalityModifier;
import com.cutlerdevelopment.centurionconsole.R;

import java.util.ArrayList;
import java.util.Map;

public class PersonalitySummarayAdapter extends BaseAdapter {
    private ArrayList<PersonalityModifier> singleRow;
    private LayoutInflater thisInflater;
    private Context context;

    public interface PersonalityListener {
        void personalityOpened(PersonalityModifier modifier);
    }
    private PersonalityListener listener;

    public PersonalitySummarayAdapter(Context context, ArrayList<PersonalityModifier> aRow, PersonalityListener listener) {
        this.singleRow = aRow;
        this.context = context;
        thisInflater = (thisInflater.from(context));
        this.listener = listener;
    }
    @Override
    public int getCount() {
        return singleRow.size();
    }

    @Override
    public Object getItem(int i) {
        return singleRow.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = thisInflater.inflate(R.layout.view_personality_summary, parent, false);
        }

        Button modifierName = convertView.findViewById(R.id.personalitySummaryName);

        final PersonalityModifier modifier = (PersonalityModifier) getItem(i);
        modifierName.setText(modifier.getName() + "\n" + modifier.getCost());
        modifierName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.personalityOpened(modifier);
            }
        });
        if (modifier.getCost() < 0) {
            modifierName.setTextColor(context.getResources().getColor(R.color.colorRed));
        }
        else {
            modifierName.setTextColor(context.getResources().getColor(R.color.colorGreen));

        }
        return convertView;

    }
}
