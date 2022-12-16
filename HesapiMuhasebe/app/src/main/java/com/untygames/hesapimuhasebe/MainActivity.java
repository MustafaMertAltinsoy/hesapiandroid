package com.untygames.hesapimuhasebe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static int musteriSayisi;

    public static FirebaseUser user;

    public void musterileriYukle(){
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DilAyarla();
        musterileriYukle();
        Toast.makeText(this, user.getUid(), Toast.LENGTH_SHORT).show();
    }

    public void kisilerAc(View view) {
        startActivity(new Intent(MainActivity.this, Kisiler_Ekrani.class));
        finish();
    }
    public void DilAyarla() {
        String languageToLoad  = "tr"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    public void hareketler(View view) {
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.hareketlerBtn);
        Animation a = AnimationUtils.loadAnimation(this,R.anim.bakiye_acilma);
        rl.startAnimation(a);
    }
}