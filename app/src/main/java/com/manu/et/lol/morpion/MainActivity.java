package com.manu.et.lol.morpion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView settings;

    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = findViewById(R.id.settings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent activi = new Intent(getApplicationContext(), com.manu.et.lol.morpion.settings.class);
                startActivity(activi);
            }
        });

        findViewById(R.id.deuxjoueur).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ttt = new Intent(getApplicationContext(), com.manu.et.lol.morpion.deuxjoueur.class);
                startActivity(ttt);
            }
        });


    }

    public void clickNiveau(View view) {
        int niveau;


        switch (view.getId()) {
            case R.id.nivun :
                niveau = 1;
                break;

            case R.id.nivdeux :
                niveau = 2;
                break;

            case R.id.nivtrois :
                niveau = 3;
                break;

            default:
                return;
        }

        Intent ddd = new Intent(getApplicationContext(), com.manu.et.lol.morpion.unjoueur.class);
        ddd.putExtra("niveau", niveau);
        startActivity(ddd);
    }
}
