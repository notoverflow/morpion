package com.notoverflow.morpion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.notoverflow.morpion.unjoueur;


public class MainActivity extends AppCompatActivity {

    private ImageView settings;
    private  Button    difficile;
    private AdRequest adRequest;
    private  AdView    adView;

    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        loadPub();

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
