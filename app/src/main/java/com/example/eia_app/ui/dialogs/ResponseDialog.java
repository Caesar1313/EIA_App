package com.example.eia_app.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ResponseDialog extends AppCompatDialogFragment {
    String response;

    public ResponseDialog(String response){
        this.response = response;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(response);
        return builder.create();
    }
}
