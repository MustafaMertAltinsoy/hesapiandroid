package com.untygames.hesapimuhasebe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ParaGiris extends AppCompatActivity {

    public static int hangiYer;//1==Add//2==Edit
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_para_giris);
    }

    private boolean hangiTarafSecili;

    public void SagTarafSec(View view) {
        hangiTarafSecili=true;
    }

    public void SolTarafSec(View view) {
        hangiTarafSecili=false;
    }

    public void geriGit(View view) {
        startActivity(new Intent(ParaGiris.this,HareketAddActivity.class));
        finish();
    }

    private String solParaStr = "";
    private String sagParaStr = "";

    public void Kaydet(int para){
        if (!hangiTarafSecili) {//Sol
            if (para < 0 && solParaStr.length() > 0 && solParaStr != "") {
                solParaStr = solParaStr.substring(0, solParaStr.length() - 1);
            }

            if (para >= 0 && solParaStr.length() < 7) {
                solParaStr += para;
            }
            TextView solTaraf = (TextView) findViewById(R.id.para_SolTaraf);
            solTaraf.setText(solParaStr);
        }
        if (hangiTarafSecili) {//Sag
            if (para < 0 && sagParaStr.length() > 0 && sagParaStr != "") {
                sagParaStr = sagParaStr.substring(0, sagParaStr.length() - 1);
            }
            if (para >= 0 && sagParaStr.length() < 2) {
                sagParaStr += para;
            }
            TextView sagTaraf = (TextView) findViewById(R.id.para_SagTaraf);
            sagTaraf.setText(sagParaStr);
        }
    }
    public void para_0(View view) {
        //paraStr+="0";
        Kaydet(0);
    }
    public void para_1(View view) {
        //paraStr+="1";
        Kaydet(1);
    }
    public void para_2(View view) {
        //paraStr+="2";
        Kaydet(2);
    }
    public void para_3(View view) {
        //paraStr+="3";
        Kaydet(3);
    }
    public void para_4(View view) {
        //paraStr+="4";
        Kaydet(4);
    }
    public void para_5(View view) {
        //paraStr+="5";
        Kaydet(5);
    }
    public void para_6(View view) {
        //paraStr+="6";
        Kaydet(6);
    }
    public void para_7(View view) {
        //paraStr+="7";
        Kaydet(7);
    }
    public void para_8(View view) {
        //paraStr+="8";
        Kaydet(8);
    }
    public void para_9(View view) {
        //paraStr+="9";
        Kaydet(9);
    }
    public void para_X(View view) {
        Kaydet(-1);
    }
    private float sol;
    private float sag;
    public void para_Ok(View view) {
        if (sagParaStr.length() == 1) {
            sagParaStr += "0";
        }
        try {
            sol = Float.parseFloat(solParaStr);
        } catch (NumberFormatException exp) {
            sol = 0;
        }
        try {
            sag = Float.parseFloat(sagParaStr);
        } catch (NumberFormatException exp) {
            sag = 0;
        }

        if(hangiYer == 1) {
            float toplamTutar = sol + (sag / 100);
            HareketAddActivity.tutarText = toplamTutar;

            startActivity(new Intent(ParaGiris.this, HareketAddActivity.class));
            finish();
        }
        if(hangiYer == 2) {
            float toplamTutar = sol + (sag / 100);
            DuzenleActivity.tutarText = toplamTutar;

            startActivity(new Intent(ParaGiris.this, DuzenleActivity.class));
            finish();
        }
    }
}