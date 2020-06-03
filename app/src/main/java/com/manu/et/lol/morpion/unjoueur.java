package com.manu.et.lol.morpion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class unjoueur extends AppCompatActivity {

    private ImageView cas[] = new ImageView[9];

    private int      cochee[]    = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int      joueurgagne = 0;
    private int      joueur      = 1;
    private TextView probleme;
    private Question question;
    private boolean  isGrille    = false;
    private int      i;
    private int      niveau;
    private boolean  caseordi    = true;
    private int      casereflex;
    private int tour = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unjoueur);


        cas[0]   = findViewById(R.id.caseun);
        cas[1]   = findViewById(R.id.casedeux);
        cas[2]   = findViewById(R.id.casetrois);
        cas[3]   = findViewById(R.id.casequatre);
        cas[4]   = findViewById(R.id.casecinq);
        cas[5]   = findViewById(R.id.casesix);
        cas[6]   = findViewById(R.id.casesept);
        cas[7]   = findViewById(R.id.casehuit);
        cas[8]   = findViewById(R.id.caseneuf);
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


                        isGrille = true;

                        break;
                }

            }
        });


    }


    public void clickCase(View view) {


        final int caseTapee;

        caseTapee = (int) view.getTag();

        Log.i("ttt", "Case tapée : " + caseTapee);

        int caserand;
        caserand = 3;

//niveau un:facile
        if (niveau == 1) {

            tour++;

            if (joueur == 1) {
                if (cochee[caseTapee] != 0) {

                    probleme.setVisibility(View.VISIBLE);
                    probleme.setText(R.string.probleme);

                    return;
                } else {

                    Log.i("ttt", "suite...");
                    cochee[caseTapee] = 1;
                    cas[caseTapee].setImageResource(R.drawable.rond);

                    if (tour == 9) {
                        test();
                    } else {
                        joueur = 2;
                    }


                }
            }

            if (joueur == 2) {


                int nb;

                nb = 0;

                caserand = (int) (Math.random() * (9 - 1));
                while (cochee[caserand] != 0 && nb < 9) {
                    caserand = (int) (Math.random() * (9 - 1));
                    nb++;
                }

                if (nb == 9) {
                    // Plus de case

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
        if (niveau == 2) {


            if (joueur == 1) {
                if (cochee[caseTapee] != 0) {

                    probleme.setVisibility(View.VISIBLE);
                    probleme.setText(R.string.probleme);

                    return;
                } else {


                    cochee[caseTapee] = 1;
                    cas[caseTapee].setImageResource(R.drawable.rond);

                    joueur = 2;

                }
            }
            test();
            if (joueur == 2) {

                caseordi = true;

                if (cochee[0] == cochee[1] && cochee[1] == 1 && caseordi) {
                    joue(2);
                }else if (cochee[2] == cochee[1] && cochee[2] == 1 && caseordi) {
                    joue(0);

                }else if (cochee[0] == cochee[2] && cochee[0] == 1 && caseordi) {
                    joue(1);

                }else if (cochee[3] == cochee[4] && cochee[4] == 1 && caseordi) {
                    joue(5);

                }else if (cochee[4] == cochee[5] & cochee[4] == 1 && caseordi) {
                    joue(3);

                }else if (cochee[3] == cochee[5] && cochee[3] == 1 && caseordi) {
                    joue(4);

                }else if (cochee[6] == cochee[7] && cochee[7] == 1 && caseordi) {
                    joue(8);

                }else if (cochee[7] == cochee[8] & cochee[7] == 1 && caseordi) {
                    joue(6);

                }else if (cochee[6] == cochee[8] && cochee[6] == 1 && caseordi) {
                    joue(7);

                }else if (cochee[0] == cochee[3] && cochee[0] == 1 && caseordi) {
                    joue(6);

                }else if (cochee[0] == cochee[6] && cochee[0] == 1 && caseordi) {
                    joue(3);

                }else if (cochee[3] == cochee[6] && cochee[3] == 1 && caseordi) {
                    joue(0);

                }else if (cochee[1] == cochee[4] && cochee[1] == 1 && caseordi) {
                    joue(7);

                }else if (cochee[1] == cochee[7] && cochee[1] == 1 && caseordi) {
                    joue(4);

                }else if (cochee[4] == cochee[7] && cochee[4] == 7 && caseordi) {
                    joue(1);

                }else if (cochee[2] == cochee[5] && cochee[2] == 1 && caseordi) {
                    joue(8);

                }else if (cochee[2] == cochee[8] && cochee[2] == 1 && caseordi) {
                    joue(5);

                }else if (cochee[5] == cochee[8] && cochee[5] == 8 && caseordi) {
                    joue(2);

                }else if (cochee[0] == cochee[4] && cochee[0] == 1 && caseordi) {
                    joue(8);

                }else if (cochee[0] == cochee[8] && cochee[0] == 1 && caseordi) {
                    joue(4);
                }else if (cochee[4] == cochee[8] && cochee[4] == 1 && caseordi) {
                    joue(0);

                }else if (cochee[2] == cochee[4] && cochee[2] == 1 && caseordi) {
                    joue(6);

                }else if (cochee[2] == cochee[6] && cochee[2] == 1 && caseordi) {
                    joue(4);

                }else if (cochee[4] == cochee[6] && cochee[4] == 1 && caseordi) {
                    joue(2);

                }else if (joueur == 2) {

                    caseordi = true;

                }

                if (caseordi) {

                    caserand = (int) (Math.random() * (9 - 1));
                    while (cochee[caserand] != 0) {
                        caserand = (int) (Math.random() * (9 - 1));

                    }
                    cochee[caserand] = 2;
                    cas[caserand].setImageResource(R.drawable.croix);
                    caseordi = false;
                    test();
                }


                joueur = 1;
            }


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


    }

    private void joue(int c) {
        if (cochee[c] == 0) {
            cas[c].setImageResource(R.drawable.croix);
            cochee[c] = 2;
            caseordi  = false;
        }
        test();
        joueur = 1;
    }

    private void test() {


        if (cochee[0] != 0 && cochee[0] == cochee[1] && cochee[1] == cochee[2]) {
            joueurgagne = cochee[0];
        }

        if (cochee[3] != 0 && cochee[3] == cochee[4] && cochee[4] == cochee[5]) {
            joueurgagne = cochee[4];
        }

        if (cochee[6] != 0 && cochee[6] == cochee[7] && cochee[7] == cochee[8]) {
            joueurgagne = cochee[6];
        }

        if (cochee[0] != 0 && cochee[0] == cochee[3] && cochee[3] == cochee[6]) {
            joueurgagne = cochee[0];
        }

        if (cochee[1] != 0 && cochee[1] == cochee[4] && cochee[4] == cochee[7]) {
            joueurgagne = cochee[7];
        }

        if (cochee[2] != 0 && cochee[2] == cochee[5] && cochee[5] == cochee[8]) {

            joueurgagne = cochee[8];

        }

        if (cochee[0] != 0 && cochee[0] == cochee[4] && cochee[4] == cochee[8]) {

            joueurgagne = cochee[8];

        }

        if (cochee[2] != 0 && cochee[2] == cochee[4] && cochee[4] == cochee[6]) {

            joueurgagne = cochee[2];


        }
    }
}
