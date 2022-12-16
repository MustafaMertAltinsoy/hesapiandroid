package com.untygames.hesapimuhasebe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MusteriEkle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musteri_ekle);
    }

    public void musteriEkle(View view) {
        EditText adSoyad=(EditText) findViewById(R.id.adSoyadInput);
        EditText telefon=(EditText) findViewById(R.id.telefonInput);
        EditText not=(EditText) findViewById(R.id.notInput);

        String adSoyadStr = adSoyad.getText().toString();
        String telefonStr = telefon.getText().toString();
        String notStr = not.getText().toString();

        if(!adSoyadStr.equals("") && !adSoyadStr.equals(" ") && !adSoyadStr.equals("  ")) {

            SharedPreferences musteriler = getSharedPreferences("preferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = musteriler.edit();

            /*String[] adSoyadSplit = adSoyadStr.split("-");
            String adSoyadCode = adSoyadSplit[0];*/

            MainActivity.musteriSayisi += 1;
            editor.putString("musteri_" + MainActivity.musteriSayisi + "_ad", adSoyadStr);
            editor.putString("musteri_" + MainActivity.musteriSayisi + "_telefon", telefonStr);
            editor.putString("musteri_" + MainActivity.musteriSayisi + "_aciklama", notStr);
            editor.putBoolean("musteri_" + MainActivity.musteriSayisi + "_isdeleted", false);

            editor.putInt("musteriSayisi", MainActivity.musteriSayisi);

            editor.commit();

            startActivity(new Intent(MusteriEkle.this, Kisiler_Ekrani.class));
            finish();
            Toast.makeText(MusteriEkle.this,"Müşteri başarıyla eklendi",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MusteriEkle.this,"Ad soyad kısmı boş bırakılamaz",Toast.LENGTH_SHORT).show();
        }

    }

    public void geriDon(View view) {
        startActivity(new Intent(MusteriEkle.this, Kisiler_Ekrani.class));
        finish();
    }
}