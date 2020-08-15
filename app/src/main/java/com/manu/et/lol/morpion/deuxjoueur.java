package com.notoverflow.morpion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.notoverflow.morpion.Question;

public class deuxjoueur extends AppCompatActivity {


    private ImageView cas[] = new ImageView[9];
    private int cochee[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};

    private int i;
    private int joueurgagne=0;
    private int joueur=1;
    private TextView probleme;


    private boolean isGrille = false;

    private Question question;

    private AdRequest adRequest;
    private AdView    adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deuxjoueur);



        loadPub();

        cas[0] = findViewById(R.id.caseun);
        cas[1]= findViewById(R.id.casedeux);
        cas[2]= findViewById(R.id.casetrois);
        cas[3]= findViewById(R.id.casequatre);
        cas[4]= findViewById(R.id.casecinq);
        cas[5]= findViewById(R.id.casesix);
        cas[6]= findViewById(R.id.casesept);
        cas[7]= findViewById(R.id.casehuit );
        cas[8]= findViewById(R.id.caseneuf);
        probleme = findViewById(R.id.probleme);


        for (i = 0; i < 9; i++) cas[i].setTag(i);

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isGrille) question.show();
            }
        });

        question = new Question(this);

        question.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {



                switch (question.getRetour()) {

                    case Question.oui:
                        joueurgagne = 0;
                        joueur = 1;

                        for (int n = 0; n < 9; n++) {
                            cochee[n] = 0;
                            cas[n].setImageResource(R.drawable.vide);
                            cas[n].setClickable(true);
                        }
                        i = 0;

                        isGrille = false;

                        break;

                    case Question.non:

                        isGrille = false;

                        finish();
                        break;


                    case Question.grille:


                        isGrille =true;

                        break;
                }

            }
        });


    }


    public void clickCase(View view) {
        final int caseTapee;

        caseTapee = (int) view.getTag();

        if (cochee[caseTapee] != 0)
        {

            probleme.setVisibility(View.VISIBLE);
            probleme.setText(R.string.probleme);

            return;
        }


        if (cochee[caseTapee] == 0) {

            probleme.setVisibility(View.INVISIBLE);
            if (joueur == 1) {

                cochee[caseTapee] = 1;
                cas[caseTapee].setImageResource(R.drawable.rond);

                joueur = 2;
            } else {
                cochee[caseTapee] = 2;
                cas[caseTapee].setImageResource(R.drawable.croix);

                joueur = 1;
            }
        }

        if (cochee[0] != 0 && cochee[0]==cochee[1] && cochee[1]==cochee[2]) {

            joueurgagne = cochee[0];

        }

        if (cochee[3] != 0 && cochee[3]==cochee[4] && cochee[4]==cochee[5]) {

            joueurgagne = cochee[4];
        }

        if (cochee[6] != 0 && cochee[6]==cochee[7] && cochee[7]==cochee[8]) {

            joueurgagne = cochee[6];
        }

        if (cochee[0] != 0 && cochee[0]==cochee[3] && cochee[3]==cochee[6]) {

            joueurgagne = cochee[0];

        }

        if (cochee[1] != 0 && cochee[1]==cochee[4] && cochee[4]==cochee[7]) {

            joueurgagne = cochee[7];

        }

        if (cochee[2] != 0 && cochee[2]==cochee[5] && cochee[5]==cochee[8]) {

            joueurgagne = cochee[8];

        }

        if (cochee[0] != 0 && cochee[0]==cochee[4] && cochee[4]==cochee[8]) {

            joueurgagne = cochee[8];

        }

        if (cochee[2] != 0 && cochee[2]==cochee[4] && cochee[4]==cochee[6]) {

            joueurgagne = cochee[2];


        }


        if (joueurgagne == 1) {

            for (int i=0;i<9;i++)
                cas[i].setClickable(false);
            question.setMessage(R.string.joueur1gagner);


        }

        if (joueurgagne == 2) {

            for (int i=0;i<9;i++)
                cas[i].setClickable(false);
            question.setMessage(R.string.joueur2gagner);
        }

        boolean plein = true;

        for (int a = 0; a < 9; a++) {
            if (cochee[a] == 0) {
                plein = false;
            }
        }

        if (plein && joueurgagne != 1 && joueurgagne != 2) {
            question.setMessage(R.string.egalite);
            question.show();

        }

        if (joueurgagne == 1 || joueurgagne == 2) {


            question.show();
        }

    }


    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (adView != null) {
            adView.resume();

        }
    }

    @Override
    protected void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();

        }
        super.onDestroy();
    }

    private void loadPub() {
        adRequest = new AdRequest.Builder()
                .addTestDevice("72BC668537B5617DBB7381C8C100AF34")      // id dui device de test pour les pubs
                .build();

        adView=findViewById(R.id.adView);
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }
        });


    }



}
