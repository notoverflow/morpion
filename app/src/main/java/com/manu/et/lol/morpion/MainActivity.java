package com.notoverflow.morpion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.notoverflow.morpion.unjoueur;


public class MainActivity extends AppCompatActivity {

    private ImageView settings;
    private Button difficile;

    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = findViewById(R.id.settings);
        settings.setVisibility(View.INVISIBLE);
        difficile = findViewById(R.id.nivtrois);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent activi = new Intent(getApplicationContext(), com.notoverflow.morpion.settings.class);
                startActivity(activi);
            }
        });

        findViewById(R.id.deuxjoueur).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ttt = new Intent(getApplicationContext(), com.notoverflow.morpion.deuxjoueur.class);
                startActivity(ttt);
            }
        });


    }

    public void clickNiveau(View view) {
        int niveau;


        switch (view.getId()) {
            case R.id.nivun :
                niveau = unjoueur.NIVEAU_FACILE;
                break;
            case R.id.nivdeux :

                niveau = unjoueur.NIVEAU_MOYEN;
                break;

            case R.id.nivtrois :
                niveau = unjoueur.NIVEAU_DIFFICILE;
                break;

            default:
                return;
        }

            Intent ddd = new Intent(getApplicationContext(), com.notoverflow.morpion.unjoueur.class);
            ddd.putExtra("niveau", niveau);
            startActivity(ddd);
    }
}
