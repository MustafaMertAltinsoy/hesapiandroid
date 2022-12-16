package com.untygames.hesapimuhasebe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.fonts.FontFamily;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Kisiler_Ekrani extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private List<RelativeLayout> itemsListLayout = new ArrayList<>();
    private List<TextView> itemsList = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kisiler_ekrani);
        MusteriOku();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        EditText searchEditText = (EditText) findViewById(R.id.kisilerSearchBar);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    List<String> kodlar = new ArrayList<>();
    List<String> adlar = new ArrayList<>();
    List<String> soyadlar = new ArrayList<>();
    List<String> lakaplar = new ArrayList<>();
    List<String> isDeleted = new ArrayList<>();
    String musteriIsimler = "";

    private void MusteriOku(){
        mDatabase.child("musteriler").child(MainActivity.user.getUid()).child("musteriSay").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                     String abc = String.valueOf(task.getResult().getValue());
                    Toast.makeText(Kisiler_Ekrani.this, abc, Toast.LENGTH_SHORT).show();
                     //musteriSayisi = Integer.parseInt(abc);
                }
                else {
                }
            }
        });

        DatabaseReference musteriCekmeRef = database.getReference(MainActivity.user.getUid() + "/musteriIsimler");
        musteriCekmeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                musteriIsimler = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        //                musteriIsimler += ad + "~" + soyad + "~" + lakap + "~" + "false" + "~";

        String[] adlarSoyadlarLakaplar = musteriIsimler.split("~");

        for (int i = 0; i < musteriSayisi; i++)
        {
            kodlar.add(i+"");
        }

        int a = 0;
        for (int i = 0; i < adlarSoyadlarLakaplar.length - 1; i++)
        {
            if (a == 0)
            {
                adlar.add(adlarSoyadlarLakaplar[i]);
                a++;
            }
            else if (a == 1)
            {
                soyadlar.add(adlarSoyadlarLakaplar[i]);
                a++;
            }
            else if (a == 2)
            {
                lakaplar.add(adlarSoyadlarLakaplar[i]);
                a++;
            }
            else if (a == 3)
            {
                isDeleted.add(adlarSoyadlarLakaplar[i]);
                a=0;
            }
        }
        musterileriYukle();
    }

    public void filter(String s){
        ArrayList<TextView> filteredList=new ArrayList<>();
        ArrayList<RelativeLayout> filteredListLayout=new ArrayList<>();

        for(int i=0; i<itemsList.size(); i++){
            RelativeLayout rl = itemsListLayout.get(i);
            TextView txt = itemsList.get(i);
            if(txt.getText().toString().toLowerCase().contains(s.toLowerCase())){
                filteredList.add(txt);
                filteredListLayout.add(rl);
            }
        }

        /*for(TextView item: itemsList){
            if(item.getText().toString().toLowerCase().contains(s.toLowerCase())){
                filteredList.add(item);
            }
        }*/
        for (RelativeLayout a : itemsListLayout) {
            a.setVisibility(View.GONE);
        }
        for (RelativeLayout a : filteredListLayout) {
            a.setVisibility(View.VISIBLE);
        }
    }

    int musteriSayisi;

    public void musterileriYukle(){

        ScrollView sc = (ScrollView) findViewById(R.id.kisilerScrollView);
        LinearLayout linear = (LinearLayout) findViewById(R.id.scrollViewLayout);
        Button musteriBtn;


        for(int i=0;i < musteriSayisi;i++){

            String isDeletedStr = isDeleted.get(i);
            String name = adlar.get(i) + " " + soyadlar.get(i) + " " + lakaplar.get(i);

            if(isDeletedStr == "false") {
                RelativeLayout rl = new RelativeLayout(this);
                itemsListLayout.add(rl);
                rl.setBackgroundResource(R.drawable.image_musteribg);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(20, 10, 10, 20);




                RelativeLayout.LayoutParams btnParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                //params.setMargins(20, 15, 20, 15);

                musteriBtn = new Button(this);
                musteriBtn.setLayoutParams(btnParams);
                musteriBtn.setText(i + "");
                musteriBtn.setId(R.id.kisilerpanel_btn);
                musteriBtn.setBackgroundResource(R.color.nothing);
                musteriBtn.setTextColor(getResources().getColor(R.color.nothing));
                String musteriAdSoyadCode = musteriBtn.getText().toString();
                musteriBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String adSoyadCode = musteriAdSoyadCode;
                        openUserPanel(adSoyadCode);
                    }
                });

                RelativeLayout.LayoutParams btnParamsRules = (RelativeLayout.LayoutParams) musteriBtn.getLayoutParams();
                btnParamsRules.addRule(RelativeLayout.LEFT_OF, R.id.kisilerpanel_btn);

                RelativeLayout.LayoutParams isimParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                TextView isim = new TextView(this);
                isim.setLayoutParams(isimParams);
                itemsList.add(isim);
                isim.setText(name);
                isim.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
                isim.setTextColor(getResources().getColor(R.color.black));
                isim.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                isim.setTextSize(20);

                RelativeLayout.LayoutParams isimParamsRules = (RelativeLayout.LayoutParams) isim.getLayoutParams();
                isimParamsRules.addRule(RelativeLayout.CENTER_IN_PARENT);
                isimParamsRules.leftMargin = 20;

                RelativeLayout.LayoutParams ucNoktaParams = new RelativeLayout.LayoutParams(75, 75);
                ImageButton ucNokta = new ImageButton(this);
                ucNokta.setLayoutParams(ucNoktaParams);
                ucNokta.setId(R.id.kisilerpanel_btn);
                ucNokta.setBackgroundResource(R.drawable.uc_nokta);
                ucNokta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        musteriEditOrDelete(ucNokta, musteriAdSoyadCode);
                    }
                });

                RelativeLayout.LayoutParams ucNoktaParamsRules = (RelativeLayout.LayoutParams) ucNokta.getLayoutParams();
                ucNoktaParamsRules.addRule(RelativeLayout.CENTER_IN_PARENT);
                ucNoktaParamsRules.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

                rl.addView(isim, isimParamsRules);
                rl.addView(musteriBtn, btnParamsRules);
                rl.addView(ucNokta, ucNoktaParamsRules);
                linear.addView(rl, params);
            }
        }
    }
    public void musteriEditOrDelete(View view,String code) {
        this.code = code;
        PopupMenu popup = new PopupMenu(this,view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu_kisiler);
        popup.show();
    }
    String code;
    @Override
    public boolean onMenuItemClick(MenuItem item){
        SharedPreferences musteriler = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = musteriler.edit();

        switch (item.getItemId()){
            case R.id.kisiler_item1:
                //DuzenleActivity.activeCode = activeCode;
                KisiyiDuzenleActivity.code = code;
                startActivity(new Intent(Kisiler_Ekrani.this, KisiyiDuzenleActivity.class));
                finish();
                return true;
            case R.id.kisiler_item2:
                editor.putBoolean("musteri_" + code + "_isdeleted", true);
                editor.remove("musteri_" + code + "_ad");
                editor.remove("musteri_" + code + "_telefon");
                editor.remove("musteri_" + code + "_aciklama");

                startActivity(new Intent(Kisiler_Ekrani.this,Kisiler_Ekrani.class));
                Toast.makeText(this,"Başarıyla silindi",Toast.LENGTH_SHORT).show();
                editor.commit();
                return true;
            default:
                return false;
        }
    }
    public void openUserPanel(String code) {
        SharedPreferences musteriler = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        HareketAddActivity.activeCode = Integer.parseInt(code);
        HareketAddActivity.islemSayisi = musteriler.getInt("musteri_" + Integer.parseInt(code) + "_islemSayisi_",0);

        startActivity(new Intent(Kisiler_Ekrani.this, UserPanelActivity.class));
        finish();
    }

    public void kisiEkle(View view) {
        startActivity(new Intent(Kisiler_Ekrani.this, MusteriEkle.class));
        finish();
    }

    public void geriGit(View view) {
        startActivity(new Intent(Kisiler_Ekrani.this, MainActivity.class));
        finish();
    }
}

/*int enYuksekPuan = ayarlar.getInt("enYuksek", 0);*/
