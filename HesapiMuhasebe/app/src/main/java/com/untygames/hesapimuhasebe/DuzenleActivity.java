package com.untygames.hesapimuhasebe;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class DuzenleActivity extends AppCompatActivity {

    public static String islemCode;
    public static int activeCode;


    public static Float tutarText = 0.0f;

    public static String aciklamaText;
    public static String tarihText;
    public static Boolean kaydetmeBoolBorcAlacak = true;

    public void hareketleriYukle(){
        SharedPreferences musteriler = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        String name = musteriler.getString("musteri_" + activeCode + "_ad","");
        TextView txt = (TextView) findViewById(R.id.cariIsim);
        TextView userPanelName = (TextView) findViewById(R.id.userPanelName);
        txt.setText(name);
        userPanelName.setText(name);

        //VerileriDegismemesiniSaglama
        TextView tutarText = (TextView) findViewById(R.id.tutarTextEdit);
        TextView tarihText=(TextView) findViewById(R.id.tarihInputEdit);
        EditText aciklamaText = (EditText) findViewById(R.id.acıklamaInputEdit);


        if(this.aciklamaText == null){
            this.aciklamaText = musteriler.getString("musteri_" + activeCode + "_islem_" + islemCode + "_aciklama", "").toString();
        }else{
            aciklamaText.setText(this.aciklamaText);
        }

        if(this.tarihText == null){
            this.tarihText = musteriler.getString("musteri_" + activeCode + "_islem_" + islemCode + "_tarih", "").toString();
        }else{
            tarihText.setText(this.tarihText);
        }

        if(this.tutarText == 0){
            this.tutarText=musteriler.getFloat("musteri_" + activeCode + "_islem_" + islemCode + "_tutar", 0);
        }else{
            tutarText.setText(this.tutarText + "");
        }
    }

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duzenle);
        hareketleriYukle();

        if (tarihText == null) {
            mDisplayDate = (TextView) findViewById(R.id.tarihInputEdit);

            Calendar c = Calendar.getInstance();

            String[] months = {"Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran", "Temmuz", "Ağustos", "Eylül", "Ekim", "Kasım", "Aralık"};

            mDisplayDate.setText(mDisplayDate.getText());
        }
        borcOrAlacak=kaydetmeBoolBorcAlacak;
        BorcOrAlacakDegistir();
    }

    public void tutarEkle(View view) {
        EditText aciklamaText = (EditText) findViewById(R.id.acıklamaInputEdit);
        this.aciklamaText = aciklamaText.getText().toString();
        ParaGiris.hangiYer=2;
        startActivity(new Intent(DuzenleActivity.this,ParaGiris.class));
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
        tutarText=0f;
        SharedPreferences musteriler = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = musteriler.edit();

        TextView tutarText = (TextView) findViewById(R.id.tutarTextEdit);
        float tutar = Float.parseFloat(tutarText.getText().toString());

        TextView tarihText=(TextView) findViewById(R.id.tarihInputEdit);
        String tarih = tarihText.getText().toString();

        EditText aciklamaText = (EditText) findViewById(R.id.acıklamaInputEdit);
        String aciklama = aciklamaText.getText().toString();

        editor.putBoolean("musteri_" + activeCode + "_islem_" + islemCode + "_alacakORborc", borcOrAlacak);
        editor.putFloat("musteri_" + activeCode + "_islem_" + islemCode + "_tutar", tutar);
        editor.putString("musteri_" + activeCode + "_islem_" + islemCode + "_tarih", tarih);
        editor.putString("musteri_" + activeCode + "_islem_" + islemCode + "_aciklama", aciklama);

        editor.commit();

        startActivity(new Intent(DuzenleActivity.this, UserPanelActivity.class));
        finish();
    }

    public void tarihSec(View view){
        mDisplayDate = (TextView) findViewById(R.id.tarihInputEdit);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(DuzenleActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String[] months = {"","Ocak","Şubat","Mart","Nisan","Mayıs","Haziran","Temmuz","Ağustos","Eylül","Ekim","Kasım","Aralık"};
                String date = day + " " + months[month] + " " + year;
                tarihText = date;
                mDisplayDate.setText(date);
            }
        };
    }

    public void geriGit(View view) {
        startActivity(new Intent(DuzenleActivity.this, UserPanelActivity.class));
        finish();
    }
}