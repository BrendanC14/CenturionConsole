package com.cutlerdevelopment.centurionconsole.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Person;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import androidx.fragment.app.DialogFragment;

import com.cutlerdevelopment.centurionconsole.Data.DataHolder;
import com.cutlerdevelopment.centurionconsole.Models.PersonalityModifier;
import com.cutlerdevelopment.centurionconsole.R;
import com.cutlerdevelopment.centurionconsole.ViewAdapters.PersonalitySummarayAdapter;

public class PersonalityModifiersFragment extends DialogFragment {

    private PersonalitySummarayAdapter.PersonalityListener listener;

    public PersonalityModifiersFragment(PersonalitySummarayAdapter.PersonalityListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View addPersonalityView = inflater.inflate(R.layout.dialog_add_personality, null);
        GridView addPersonalityGrid = addPersonalityView.findViewById(R.id.addPersonalityGrid);

        PersonalitySummarayAdapter adapter = new PersonalitySummarayAdapter(
                getContext(),
                DataHolder.getInstance().getPersonalityModifiers(),
                listener
        );
        addPersonalityGrid.setAdapter(adapter);
        addPersonalityGrid.setNumColumns(2);


        builder.setView(addPersonalityView);
        return builder.create();
    }
}
