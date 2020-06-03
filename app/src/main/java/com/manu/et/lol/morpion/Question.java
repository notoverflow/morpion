package com.manu.et.lol.morpion;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.manu.et.lol.morpion.R.layout;

import static android.view.Window.*;

public class Question extends Dialog {

    public static final int oui = 0;
    public static final int non = 2;
    public static final int grille = 3;
    public int retour = 0;

    private String message = "";

    private TextView textMessage;

    public Question(@NonNull Context context) {
        super(context);


        setContentView(R.layout.dialog);

        textMessage = findViewById(R.id.gagne);

        findViewById(R.id.oui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retour = oui;
                dismiss();
            }
        });

        findViewById(R.id.non).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retour = non;
                dismiss();
            }
        });

        findViewById(R.id.grille).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retour = grille;
                dismiss();
            }
        });










    }

    public int getRetour(){
        return retour;
    }

    public void setMessage(int message) {
        textMessage.setText(message);
    }

}
