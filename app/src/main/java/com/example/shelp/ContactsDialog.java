package com.example.shelp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ContactsDialog extends AppCompatDialogFragment {

    private EditText contactName, contactNumber;
    private ContactDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.contact_dialog,null);

        builder.setView(view)
                .setTitle("Add Contact")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = contactName.getText().toString();
                        String number = contactNumber.getText().toString();
                        listener.applyTexts(name, number);
                    }
                });

        contactName = view.findViewById(R.id.cn_ed_name);
        contactNumber = view.findViewById(R.id.cn_ed_number);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ContactDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ContactDialogListener");
        }
    }

    public interface ContactDialogListener{
        void applyTexts(String Name, String Number);
    }

}
