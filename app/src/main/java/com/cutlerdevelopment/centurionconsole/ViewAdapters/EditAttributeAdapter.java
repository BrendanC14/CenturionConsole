package com.cutlerdevelopment.centurionconsole.ViewAdapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cutlerdevelopment.centurionconsole.Constants.AttributeTypes;
import com.cutlerdevelopment.centurionconsole.Helpers.AttributeHelper;
import com.cutlerdevelopment.centurionconsole.Models.Attribute;
import com.cutlerdevelopment.centurionconsole.R;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.List;

public class EditAttributeAdapter extends BaseAdapter {
    private ArrayList<Attribute> singleRow;
    private LayoutInflater thisInflater;
    private Context context;
    private int pointsToSpend;

    public interface EditAttributeListener {
        void attributeEdited(int pointsRemaining);
    }

    private EditAttributeListener listener;

    public EditAttributeAdapter(Context context, ArrayList<Attribute> aRow, EditAttributeListener listener) {
        this.singleRow = aRow;
        this.context = context;
        thisInflater = (thisInflater.from(context));
        pointsToSpend = 0;
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

    public ArrayList<Attribute> getAttributes() {
        return singleRow;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = thisInflater.inflate(R.layout.view_attribute_edit, parent, false);
        }

        TextView attributeName = convertView.findViewById(R.id.attributeEditName);
        TextView attributeScore = convertView.findViewById(R.id.attributeEditNumber);
        Button reduceAttributeButton = convertView.findViewById(R.id.attributeEditReduceButton);
        Button increaseAttributeButton = convertView.findViewById(R.id.attributeEditIncreaseButton);
        Button attributeHelp = convertView.findViewById(R.id.attributeHelpButton);

        final Attribute attribute = (Attribute) getItem(i);
        attributeName.setText(AttributeHelper.getAttributeName(attribute.getType(), true));
        attributeScore.setText(String.valueOf(AttributeHelper.getAttributeLevel(attribute.getValue())));
        attributeHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHelpDialogBox(attribute.getType());
            }
        });
        reduceAttributeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reduceAttribute(attribute);
            }
        });
        increaseAttributeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increaseAttribute(attribute);
            }
        });

        if (pointsToSpend > 0 && attribute.getValue() < AttributeHelper.NEW_CENTURION_MAX_SCORE) {
            increaseAttributeButton.setVisibility(View.VISIBLE);
        }
        else {
            increaseAttributeButton.setVisibility(View.INVISIBLE);
        }

        if (attribute.getValue() > AttributeHelper.NEW_CENTURION_MIN_SCORE) {
            reduceAttributeButton.setVisibility(View.VISIBLE);
        }
        else {
            reduceAttributeButton.setVisibility(View.INVISIBLE);
        }
        return convertView;

    }

    private void showHelpDialogBox(int attributeType) {

        new AlertDialog.Builder(context)
                .setTitle(AttributeHelper.getAttributeName(attributeType, true))
                .setMessage(getAttributeHelpText(attributeType))

                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private String getAttributeHelpText(int type) {
        switch (type) {
            case AttributeTypes.STRENGTH: return context.getString(R.string.strength_help_text);
            case AttributeTypes.DEXTERITY: return context.getString(R.string.dexterity_help_text);
            case AttributeTypes.INTELLIGENCE: return context.getString(R.string.intelligence_help_text);
            case AttributeTypes.WISDOM: return context.getString(R.string.wisdom_help_text);
            case AttributeTypes.CHARISMA: return context.getString(R.string.charisma_help_text);
            case AttributeTypes.CONSTITUTION: return context.getString(R.string.constitution_help_text);
            default: return "Couldn't find text for type " + type;
        }
    }

    private void reduceAttribute(Attribute attribute) {
        attribute.reduceValue();
        pointsToSpend++;
        listener.attributeEdited(pointsToSpend);
    }
    private void increaseAttribute(Attribute attribute) {
        attribute.increaseValue();
        pointsToSpend--;
        listener.attributeEdited(pointsToSpend);
    }
}
