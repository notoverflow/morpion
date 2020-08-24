package com.notoverflow.morpion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.notoverflow.morpion.Question;


public class unjoueur extends AppCompatActivity {


    // Des constantes :

    private AdRequest adRequest;
    private AdView    adView;

    public final static int NIVEAU_FACILE = 1;
    public final static int NIVEAU_MOYEN = 2;
    public final static int NIVEAU_DIFFICILE = 3;

    private ImageView cas[] = new ImageView[9];

    private int cochee[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int joueurgagne=0;
    private int joueur=1;
    private TextView probleme;
    private Question question;
    private boolean isGrille = false;
    private int i;
    private int niveau;
    private boolean caseordi=true;
    private int casereflex;
    private int caserand;
    public boolean casereflechie=true;

    private static final String ID_INTER = "ca-app-pub-9696781235898812/3218963278";
    private InterstitialAd interstitialAd;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unjoueur);

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



        Intent temp;
        temp = getIntent();

        niveau = temp.getIntExtra("niveau", 42);

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
                        if (interstitialAd != null && interstitialAd.isLoaded()) {
                            interstitialAd.show();
                        } else {
                            rejouer();
                        }
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
    private void rejouer() {

        joueurgagne = 0;
        joueur = 1;

        for (int n = 0; n < 10; n++) {
            cochee[n] = 0;
            cas[n].setImageResource(R.drawable.vide);
            cas[n].setClickable(true);
        }
        i = 0;

        isGrille = false;
    }






    public void clickCase(View view) {


        final int caseTapee;

        caseTapee = (int) view.getTag();

        Log.i("ttt", "Case tapée : " + caseTapee);
        Log.i("ttt", "niveau : " + niveau);



//niveau un:facile
        if (niveau==NIVEAU_FACILE) {


            if (joueur == 1) {
                if (cochee[caseTapee] != 0) {

                    probleme.setVisibility(View.VISIBLE);
                    probleme.setText(R.string.probleme);

                    return;
                } else {

                    Log.i("ttt", "suite...");
                    cochee[caseTapee] = 1;
                    cas[caseTapee].setImageResource(R.drawable.rond);

                    joueur = 2;

                }
            }
            test();

            if (joueur == 2 && joueurgagne == 0) {


                int nb;

                nb = 0;


                caserand = (int) (Math.random() * (9 - 1));
                while (cochee[caserand] != 0 && nb < 9) {
                    caserand = (int) (Math.random() * (9 - 1));
                    nb++;


                }

                if (nb == 9 && joueurgagne == 0) {



                } else {

                    cochee[caserand] = 2;
                    cas[caserand].setImageResource(R.drawable.croix);


                    joueur = 1;

                }


            }

            test();


            if (joueurgagne == 1) {

                for (int i = 0; i < 9; i++)
                     cas[i].setClickable(false);
                question.setMessage(R.string.vousgagner);


            }

            if (joueurgagne == 2) {

                for (int i = 0; i < 9; i++)
                     cas[i].setClickable(false);
                question.setMessage(R.string.ordigagner);
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
//        niveau2: moyen bloque si possible

        else {
            // c'est le niveau moyen ou dificile

            if (joueur == 1) {
                if (cochee[caseTapee] != 0) {

                    probleme.setVisibility(View.VISIBLE);
                    probleme.setText(R.string.probleme);

                    return;
                } else {


                    probleme.setVisibility(View.INVISIBLE);
                    cochee[caseTapee] = 1;
                    cas[caseTapee].setImageResource(R.drawable.rond);

                    joueur = 2;

                    Log.i("ttt", "joueur 1 : " + caseTapee);

                }
            }

            test();

            if (joueur == 2 && joueurgagne == 0) {  // stupide car joueur forcément à deux


                Log.i("ttt", "joueur 2");

                caseordi = true;
                // on cherche à gagner

                if (niveau == NIVEAU_DIFFICILE) {

                    if (cochee[0] == cochee[1] && cochee[1] == 2 && cochee[2] == 0 && caseordi) {
                        joue(2);
                    }

                    if (cochee[2] == cochee[1] && cochee[2] == 2 && cochee[0] == 0 && caseordi) {

                        joue(0);

                    }
                    if (cochee[0] == cochee[2] && cochee[0] == 2 && cochee[1] == 0 && caseordi) {
                        joue(1);

                    }
                    if (cochee[3] == cochee[4] && cochee[4] == 2 && cochee[5] == 0 && caseordi) {
                        joue(5);

                    }
                    if (cochee[4] == cochee[5] & cochee[4] == 2 && cochee[3] == 0 && caseordi) {
                        joue(3);

                    }
                    if (cochee[3] == cochee[5] && cochee[3] == 2 && cochee[4] == 0 && caseordi) {
                        joue(4);

                    }
                    if (cochee[6] == cochee[7] && cochee[7] == 2 && cochee[8] == 0 && caseordi) {

                        joue(8);

                    }
                    if (cochee[7] == cochee[8] & cochee[7] == 2 && cochee[6] == 0 && caseordi) {

                        joue(6);

                    }
                    if (cochee[6] == cochee[8] && cochee[6] == 2 && cochee[7] == 0 && caseordi) {
                        joue(7);

                    }
                    if (cochee[0] == cochee[3] && cochee[0] == 2 && cochee[6] == 0 && caseordi) {
                        joue(6);

                    }
                    if (cochee[0] == cochee[6] && cochee[0] == 2 && cochee[3] == 0 && caseordi) {
                        joue(3);

                    }
                    if (cochee[3] == cochee[6] && cochee[3] == 2 && cochee[0] == 0 && caseordi) {
                        joue(0);

                    }
                    if (cochee[1] == cochee[4] && cochee[1] == 2 && cochee[7] == 0 && caseordi) {
                        joue(7);

                    }
                    if (cochee[1] == cochee[7] && cochee[1] == 2 && cochee[4] == 0 && caseordi) {
                        joue(4);

                    }
                    if (cochee[4] == cochee[7] && cochee[4] == 2 && cochee[1] == 0 && caseordi) {
                        joue(1);

                    }
                    if (cochee[2] == cochee[5] && cochee[2] == 2 && cochee[8] == 0 && caseordi) {
                        joue(8);

                    }
                    if (cochee[2] == cochee[8] && cochee[2] == 2 && cochee[5] == 0 && caseordi) {
                        joue(5);

                    }
                    if (cochee[5] == cochee[8] && cochee[5] == 2 && cochee[2] == 0 && caseordi) {
                        joue(2);

                    }
                    if (cochee[0] == cochee[4] && cochee[0] == 2 && cochee[8] == 0 && caseordi) {
                        joue(8);


                    }
                    if (cochee[0] == cochee[8] && cochee[0] == 2 && cochee[4] == 0 && caseordi) {
                        joue(4);

                    }
                    if (cochee[4] == cochee[8] && cochee[4] == 2 && cochee[0] == 0 && caseordi) {
                        joue(0);

                    }
                    if (cochee[2] == cochee[4] && cochee[2] == 2 && cochee[6] == 0 && caseordi) {
                        joue(6);

                    }
                    if (cochee[2] == cochee[6] && cochee[2] == 2 && cochee[4] == 0 && caseordi) {
                        joue(4);

                    }
                    if (cochee[4] == cochee[6] && cochee[4] == 2 && cochee[2] == 0 && caseordi) {
                        joue(2);

                    }


                }


                if (joueurgagne == 0) {
                    Log.i("ttt", "joueur 2 : Pas bloqué");
                } else {

                    Log.i("ttt", "joueur 2 : bloqué");
                }

                // on cherche à bloquer
                {

                    // ligne 1
                    if (cochee[0] == cochee[1] && cochee[1] == 1 && cochee[2] == 0 && caseordi) {
                        joue(2);
                    }

                    if (cochee[2] == cochee[1] && cochee[2] == 1 && cochee[0] == 0 && caseordi) {

                        joue(0);
                    }
                    if (cochee[0] == cochee[2] && cochee[0] == 1 && cochee[1] == 0 && caseordi) {
                        joue(1);
                    }

                    // ligne2
                    if (cochee[3] == cochee[4] && cochee[4] == 1 && cochee[5] == 0 && caseordi) {
                        joue(5);
                    }
                    if (cochee[4] == cochee[5] & cochee[4] == 1 && cochee[3] == 0 && caseordi) {
                        joue(3);
                    }
                    if (cochee[3] == cochee[5] && cochee[3] == 1 && cochee[4] == 0 && caseordi) {
                        joue(4);
                    }

                    // ligne 3

                    if (cochee[6] == cochee[7] && cochee[7] == 1 && cochee[8] == 0 && caseordi) {
                        joue(8);
                    }
                    if (cochee[7] == cochee[8] & cochee[7] == 1 && cochee[6] == 0 && caseordi) {
                        joue(6);
                    }
                    if (cochee[6] == cochee[8] && cochee[6] == 1 && cochee[7] == 0 && caseordi) {
                        joue(7);
                    }

                    // colonne 1
                    if (cochee[0] == cochee[3] && cochee[0] == 1 && cochee[6] == 0 && caseordi) {
                        joue(6);
                    }
                    if (cochee[0] == cochee[6] && cochee[0] == 1 && cochee[3] == 0 && caseordi) {
                        joue(3);
                    }
                    if (cochee[3] == cochee[6] && cochee[3] == 1 && cochee[0] == 0 && caseordi) {
                        joue(0);
                    }

                    // colonne 2
                    if (cochee[1] == cochee[4] && cochee[1] == 1 && cochee[7] == 0 && caseordi) {
                        joue(7);
                    }
                    if (cochee[1] == cochee[7] && cochee[1] == 1 && cochee[4] == 0 && caseordi) {
                        joue(4);
                    }
                    if (cochee[4] == cochee[7] && cochee[4] == 7 && cochee[1] == 0 && caseordi) {
                        joue(1);
                    }

                    // colonne 3
                    if (cochee[2] == cochee[5] && cochee[2] == 1 && cochee[8] == 0 && caseordi) {
                        joue(8);
                    }
                    if (cochee[2] == cochee[8] && cochee[2] == 1 && cochee[5] == 0 && caseordi) {
                        joue(5);
                    }
                    if (cochee[5] == cochee[8] && cochee[5] == 8 && cochee[2] == 0 && caseordi) {
                        joue(2);
                    }

                    // diag 1
                    if (cochee[0] == cochee[4] && cochee[0] == 1 && cochee[8] == 0 && caseordi) {
                        joue(8);
                    }
                    if (cochee[0] == cochee[8] && cochee[0] == 1 && cochee[4] == 0 && caseordi) {
                        joue(4);
                    }
                    if (cochee[4] == cochee[8] && cochee[4] == 1 && cochee[0] == 0 && caseordi) {
                        joue(0);
                    }

                    // diag 2
                    if (cochee[2] == cochee[4] && cochee[2] == 1 && cochee[6] == 0 && caseordi) {
                        joue(6);
                    }
                    if (cochee[2] == cochee[6] && cochee[2] == 1 && cochee[4] == 0 && caseordi) {
                        joue(4);
                    }
                    if (cochee[4] == cochee[6] && cochee[4] == 1 && cochee[2] == 0 && caseordi) {
                        joue(2);
                    }
                }


                if (joueurgagne == 0) {
                    Log.i("ttt", "joueur 2 : Pas gagné");
                } else {
                    Log.i("ttt", "joueur 2 : gagné");
                }

                Log.i("ttt", "caseordi" + caseordi);

                    if (caseordi) {

                        Log.i("ttt", "pas joué");

                        caserand = (int) (Math.random() * (9 - 0));

                        while (cochee[caserand] != 0) {
                            caserand = (caserand + 1) % 9;
                        }
                        
                        joue(caserand);
                    }


                test();

//                if (caseordi&& !casereflechie) {
//                    cochee[caserand] = 2;
//                    cas[caserand].setImageResource(R.drawable.croix);
//                }


                joueur = 1;
            }


            if (joueurgagne == 1) {

                for (int i = 0; i < 9; i++)
                     cas[i].setClickable(false);
                question.setMessage(R.string.vousgagner);
                probleme.setVisibility(View.INVISIBLE);


            }

            if (joueurgagne == 2) {

                for (int i = 0; i < 9; i++)
                     cas[i].setClickable(false);
                question.setMessage(R.string.ordigagner);
                probleme.setVisibility(View.INVISIBLE);

            }


            boolean plein = true;


            for (int a = 0; a < 9; a++) {
                if (cochee[a] == 0) {
                    plein = false;
                }
            }

            if (plein) {
                question.setMessage(R.string.egalite);
                question.show();

            }

            if (joueurgagne == 1 || joueurgagne == 2) {


                question.show();
            }

        }


    }

    private void joue(int c) {
        if (cochee[c] == 0) {
            casereflechie = false;
            cas[c].setImageResource(R.drawable.croix);
            cochee[c] = 2;
            caseordi  = false;
        } else {

            casereflechie = false;

            int ttt;
            ttt = 0;
            int tour;
            tour = 0;
            int a = 0;
            int caset = 6;
            int ppp = 0;
            int truc;


            caserand = (int) (Math.random() * (9 - 1));
            while (cochee[caserand] != 0) {
                caserand = (int) (Math.random() * (9 - 1));


                while (ppp < 10) {
                    truc = 0;
                    if (cochee[truc] == 0) {


                    }

                }


                ttt++;
                tour++;
                Log.i("ttt", "random : " + ttt);
                cas[caset].setImageResource(R.drawable.croix);
                cochee[caset] = 2;
                cochee[caserand] = 0;
            }
            cochee[caset] = 2;
            cochee[caserand] = 0;
            cas[caset].setImageResource(R.drawable.croix);
            cochee[caset] = 2;
            cochee[caserand] = 0;
        }

        Log.i("tt", "Joué : " + c);
    }


    /**
     * Test si un joueur a gagné
     * Le résutat est dans le champs joueurgagne
     * 0 pas de gagnant
     */

    private void test() {
        joueurgagne = 0;  // on suppose qu'il n'y a pas de gagnant

        // ligne 1
        if (cochee[0] != 0 && cochee[0] == cochee[1] && cochee[1] == cochee[2]) {
            joueurgagne = cochee[0];
        }

        // ligne 2
        if (cochee[3] != 0 && cochee[3] == cochee[4] && cochee[4] == cochee[5]) {
            joueurgagne = cochee[4];
        }

        // ligne 3
        if (cochee[6] != 0 && cochee[6] == cochee[7] && cochee[7] == cochee[8]) {
            joueurgagne = cochee[6];
        }

        // colonne 1
        if (cochee[0] != 0 && cochee[0] == cochee[3] && cochee[3] == cochee[6]) {
            joueurgagne = cochee[0];
        }

        // colonne 2
        if (cochee[1] != 0 && cochee[1] == cochee[4] && cochee[4] == cochee[7]) {
            joueurgagne = cochee[7];
        }

        // colonne 3
        if (cochee[2] != 0 && cochee[2] == cochee[5] && cochee[5] == cochee[8]) {
            joueurgagne = cochee[8];
        }

        // diagonale 1
        if (cochee[0] != 0 && cochee[0] == cochee[4] && cochee[4] == cochee[8]) {
            joueurgagne = cochee[8];
        }

        // diagonale 2
        if (cochee[2] != 0 && cochee[2] == cochee[4] && cochee[4] == cochee[6]) {
            joueurgagne = cochee[2];
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
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(ID_INTER);
        interstitialAd.loadAd(adRequest);

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                interstitialAd.loadAd(adRequest);
            }

            @Override
            public void onAdClosed() {
                interstitialAd.loadAd(adRequest);
                rejouer();
            }
        });


    }



}
