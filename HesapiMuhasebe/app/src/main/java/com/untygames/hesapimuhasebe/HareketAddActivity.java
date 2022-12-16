package com.untygames.hesapimuhasebe;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HareketAddActivity extends AppCompatActivity {

    public static int islemSayisi;
    public static int activeCode;


    public static float tutarText = 0.0f;

    public static String aciklamaText;
    public static String tarihText;
    public static Boolean kaydetmeBoolBorcAlacak = true;

    public void DilAyarla() {
        String languageToLoad  = "tr"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    public void hareketleriYukle(){
        SharedPreferences musteriler = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        String name = musteriler.getString("musteri_" + activeCode + "_ad","");
        TextView txt = (TextView) findViewById(R.id.cariIsim);
        TextView userPanelName = (TextView) findViewById(R.id.userPanelName);
        txt.setText(name);
        userPanelName.setText(name);

        //VerileriDegismemesiniSaglama
        TextView tarihText=(TextView) findViewById(R.id.tarihInput);

        EditText aciklamaText = (EditText) findViewById(R.id.acıklamaInput);

        tarihText.setText(this.tarihText);
        aciklamaText.setText(this.aciklamaText);
    }

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_activity);
        hareketleriYukle();
        DilAyarla();

        TextView text = (TextView) findViewById(R.id.tutarText);
        text.setText(tutarText + "");

        if (tarihText == null) {
            mDisplayDate = (TextView) findViewById(R.id.tarihInput);

            Calendar c = Calendar.getInstance();

            String[] months = {"Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran", "Temmuz", "Ağustos", "Eylül", "Ekim", "Kasım", "Aralık"};

            mDisplayDate.setText(c.get(Calendar.DAY_OF_MONTH) + " " + months[c.get(Calendar.MONTH)] + " " + c.get(Calendar.YEAR));
        }
        borcOrAlacak=kaydetmeBoolBorcAlacak;
        BorcOrAlacakDegistir();
    }

    public void tutarEkle(View view) {
        EditText aciklamaText = (EditText) findViewById(R.id.acıklamaInput);
        this.aciklamaText = aciklamaText.getText().toString();
        ParaGiris.hangiYer=1;
        startActivity(new Intent(HareketAddActivity.this,ParaGiris.class));
        finish();
    }

    private boolean borcOrAlacak;//true = alacak, false = borc

    public void BorcOrAlacakDegistir() {
        /*ScrollView alacakSv = (ScrollView) findViewById(R.id.alacak);
        ScrollView borcSv = (ScrollView) findViewById(R.id.borc);*/

        Button borcBtn = (Button) findViewById(R.id.borcBtn);
        Button alacakBtn = (Button) findViewById(R.id.alacakBtn);

        ImageButton cariImage=(ImageButton) findViewById(R.id.userImage);
        TextView cariText=(TextView) findViewById(R.id.user);
        ImageButton tutarImage=(ImageButton) findViewById(R.id.tutarImage);
        TextView tutarText=(TextView) findViewById(R.id.tutar);
        ImageButton tarihImage=(ImageButton) findViewById(R.id.tarihImage);
        TextView tarihText=(TextView) findViewById(R.id.tarih);
        Button btn = (Button) findViewById(R.id.kaydetBtn);



        if(!borcOrAlacak){
            cariImage.setBackgroundResource(R.drawable.image_userkirmizi);
            cariText.setTextColor(getResources().getColor(R.color.red));
            tutarImage.setBackgroundResource(R.drawable.image_tutar);
            tutarText.setTextColor(getResources().getColor(R.color.red));
            tarihImage.setBackgroundResource(R.drawable.image_calendar);
            tarihText.setTextColor(getResources().getColor(R.color.red));
            btn.setBackgroundResource(R.drawable.image_redbtn);
            alacakBtn.setBackgroundResource(R.drawable.image_redbtn);
            borcBtn.setBackgroundResource(R.drawable.btninactive);

            borcBtn.setTextColor(getResources().getColor(R.color.black));
            alacakBtn.setTextColor(getResources().getColor(R.color.white));
        }else{
            cariImage.setBackgroundResource(R.drawable.image_useryesil);
            cariText.setTextColor(getResources().getColor(R.color.green));
            tutarImage.setBackgroundResource(R.drawable.image_paraicon);
            tutarText.setTextColor(getResources().getColor(R.color.green));
            tarihImage.setBackgroundResource(R.drawable.image_alacak_calendar);
            tarihText.setTextColor(getResources().getColor(R.color.green));
            btn.setBackgroundResource(R.drawable.image_kaydetbg);
            alacakBtn.setBackgroundResource(R.drawable.btninactive);
            borcBtn.setBackgroundResource(R.drawable.image_kaydetbg);
            borcBtn.setTextColor(getResources().getColor(R.color.white));
            alacakBtn.setTextColor(getResources().getColor(R.color.black));
        }
    }

    public void AlacakBtn(View view) {
        borcOrAlacak=true;
        kaydetmeBoolBorcAlacak=borcOrAlacak;
        BorcOrAlacakDegistir();
    }
    public void BorcBtn(View view) {
        borcOrAlacak=false;
        kaydetmeBoolBorcAlacak=borcOrAlacak;
        BorcOrAlacakDegistir();
    }

    public void hareketKaydet(View view) {
        aciklamaText=null;
        tarihText=null;
        tutarText=0;
        SharedPreferences musteriler = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = musteriler.edit();

        TextView tutarText = (TextView) findViewById(R.id.tutarText);
        float tutar = Float.parseFloat(tutarText.getText().toString());

        TextView tarihText=(TextView) findViewById(R.id.tarihInput);
        String tarih = tarihText.getText().toString();

        EditText aciklamaText = (EditText) findViewById(R.id.acıklamaInput);
        String aciklama = aciklamaText.getText().toString();

        islemSayisi++;
        editor.putInt("musteri_" + activeCode + "_islemSayisi_", islemSayisi);
        editor.putBoolean("musteri_" + activeCode + "_islem_" + islemSayisi + "_alacakORborc", borcOrAlacak);
        editor.putBoolean("musteri_" + activeCode + "_islem_" + islemSayisi + "_isdeleted", false);
        editor.putFloat("musteri_" + activeCode + "_islem_" + islemSayisi + "_tutar", tutar);
        editor.putString("musteri_" + activeCode + "_islem_" + islemSayisi + "_tarih", tarih);
        editor.putString("musteri_" + activeCode + "_islem_" + islemSayisi + "_aciklama", aciklama);

        editor.commit();

        startActivity(new Intent(HareketAddActivity.this, UserPanelActivity.class));
        finish();
    }

    public void tarihSec(View view){
        mDisplayDate = (TextView) findViewById(R.id.tarihInput);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(HareketAddActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String[] months = {"","Ocak","Şubat","Mart","Nisan","Mayıs","Haziran","Temmuz","Ağustos","Eylül","Ekim","Kasım","Aralık"};
                String date = day + " " + months[month] + " " + year;
                tarihText = date;
                mDisplayDate.setText(date);
            }
        };
    }

    public void geriGit(View view) {
        startActivity(new Intent(HareketAddActivity.this, UserPanelActivity.class));
        finish();
    }

}