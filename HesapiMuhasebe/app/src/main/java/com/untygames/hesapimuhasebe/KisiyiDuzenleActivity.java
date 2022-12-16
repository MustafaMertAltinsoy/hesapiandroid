package com.untygames.hesapimuhasebe;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class KisiyiDuzenleActivity extends AppCompatActivity {

    public static String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kisiyi_duzenle);

        init();
        DilAyarla();
    }
    public void init(){
        SharedPreferences musteriler = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        EditText adSoyad=(EditText) findViewById(R.id.adSoyadInputEdit);
        EditText telefon=(EditText) findViewById(R.id.telefonInputEdit);
        EditText not=(EditText) findViewById(R.id.notInputEdit);

        adSoyad.setText(musteriler.getString("musteri_" + code + "_ad",""));
        telefon.setText(musteriler.getString("musteri_" + code + "_telefon",""));
        not.setText(musteriler.getString("musteri_" + code + "_aciklama",""));

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
    public void musteriEkle(View view) {
        EditText adSoyad=(EditText) findViewById(R.id.adSoyadInputEdit);
        EditText telefon=(EditText) findViewById(R.id.telefonInputEdit);
        EditText not=(EditText) findViewById(R.id.notInputEdit);

        String adSoyadStr = adSoyad.getText().toString();
        String telefonStr = telefon.getText().toString();
        String notStr = not.getText().toString();

        if(!adSoyadStr.equals("") && !adSoyadStr.equals(" ") && !adSoyadStr.equals("  ")) {

            SharedPreferences musteriler = getSharedPreferences("preferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = musteriler.edit();

            /*String[] adSoyadSplit = adSoyadStr.split("-");
            String adSoyadCode = adSoyadSplit[0];*/

            editor.putString("musteri_" + code + "_ad", adSoyadStr);
            editor.putString("musteri_" + code + "_telefon", telefonStr);
            editor.putString("musteri_" + code + "_aciklama", notStr);

            editor.commit();

            startActivity(new Intent(KisiyiDuzenleActivity.this, Kisiler_Ekrani.class));
            finish();
            Toast.makeText(KisiyiDuzenleActivity.this,"Müşteri başarıyla Düzenlendi",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(KisiyiDuzenleActivity.this,"Ad soyad kısmı boş bırakılamaz",Toast.LENGTH_SHORT).show();
        }

    }

    public void geriDon(View view) {
        startActivity(new Intent(KisiyiDuzenleActivity.this, Kisiler_Ekrani.class));
        finish();
    }

}