package com.untygames.hesapimuhasebe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class UserPanelActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    public static int activeCode;
    public static int islemSayisi;

    public List<Boolean> tutarBoollar = new ArrayList<>();
    public List<Float> tutarlar = new ArrayList<>();
    public List<Float> tutarlarAlacak = new ArrayList<>();
    public List<Float> tutarlarBorc = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_hesap_panel);


        Create();
    }
    public void Create(){
        SharedPreferences musteriler = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        LinearLayout hareketlerCreator = (LinearLayout) findViewById(R.id.hareketlerCreator);


        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(25, 10, 10, 25);


        activeCode=HareketAddActivity.activeCode;
        islemSayisi=HareketAddActivity.islemSayisi;

        String name = musteriler.getString("musteri_" + activeCode + "_ad","");
        TextView userPanelName = (TextView) findViewById(R.id.userPanelName);
        userPanelName.setText(name);

        for(int i=1;i<=islemSayisi;i++) {
            Boolean alacakORborc = musteriler.getBoolean("musteri_" + activeCode + "_islem_" + i + "_alacakORborc", true);
            Boolean isDeleted = musteriler.getBoolean("musteri_" + activeCode + "_islem_" + i + "_isdeleted", false);
            String aciklama = musteriler.getString("musteri_" + activeCode + "_islem_" + i + "_aciklama", "");
            Float tutar = musteriler.getFloat("musteri_" + activeCode + "_islem_" + i + "_tutar", 0);
            String tarih = musteriler.getString("musteri_" + activeCode + "_islem_" + i + "_tarih", "");

            if (!isDeleted) {
                if(alacakORborc){
                    tutarlarAlacak.add(tutar);
                }else{
                    tutarlarBorc.add(tutar);
                }
                tutarlar.add(tutar);
                tutarBoollar.add(alacakORborc);

                RelativeLayout relativeLayout = new RelativeLayout(this);
                relativeLayout.setLayoutParams(params);
                relativeLayout.setBackgroundResource(R.drawable.kisiekleinputs);

                //Aciklama
                RelativeLayout.LayoutParams txtParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView txt = new TextView(this);
                txt.setLayoutParams(txtParam);
                //txt.setId(R.id.userpanel_aciklama);
                txt.setText(aciklama);
                txt.setTextColor(getResources().getColor(R.color.gray));
                txt.setTextSize(13);
                txt.setTypeface(txt.getTypeface(), Typeface.BOLD);

                RelativeLayout.LayoutParams txtParamRules = (RelativeLayout.LayoutParams) txt.getLayoutParams();
                txtParamRules.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                //txtParamRules.addRule(RelativeLayout.LEFT_OF, R.id.userpanel_TLImage);

                //Tarih
                RelativeLayout.LayoutParams tarihParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView tarihTxt = new TextView(this);
                tarihTxt.setLayoutParams(tarihParam);
                tarihTxt.setText(tarih);
                tarihTxt.setTextColor(getResources().getColor(R.color.gray));
                tarihTxt.setTextSize(10);
                tarihTxt.setTypeface(txt.getTypeface(), Typeface.BOLD);

                RelativeLayout.LayoutParams tarihParamRules = (RelativeLayout.LayoutParams) tarihTxt.getLayoutParams();
                tarihParamRules.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                tarihParamRules.addRule(RelativeLayout.BELOW, txt.getId());


                //ucNokta
                RelativeLayout.LayoutParams tıklamaParam = new RelativeLayout.LayoutParams(50, 50);
                Button tıklama = new Button(this);
                tıklama.setLayoutParams(tıklamaParam);
                //tıklama.setId(R.id.userpanel_ucnokta);
                tıklama.setText(i+"");
                tıklama.setTextSize(20);
                tıklama.setBackgroundResource(R.drawable.uc_nokta);

                tıklama.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String adSoyadCode = tıklama.getText().toString();
                        musteriEditOrDelete(tıklama,adSoyadCode);
                    }
                });

                RelativeLayout.LayoutParams ucNoktaParamRules = (RelativeLayout.LayoutParams) tıklama.getLayoutParams();
                ucNoktaParamRules.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                ucNoktaParamRules.addRule(RelativeLayout.CENTER_IN_PARENT);

                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String adSoyadCode = tıklama.getText().toString();
                        musteriEditOrDelete(tıklama,adSoyadCode);
                    }
                });

                //Money
                RelativeLayout.LayoutParams tutarParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView money = new TextView(this);
                money.setLayoutParams(tutarParam);
                //money.setId(R.id.userpanel_tutar);
                money.setText(tutar + "");
                money.setTextSize(18);
                money.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                money.setTypeface(money.getTypeface(), Typeface.BOLD);
                if (alacakORborc) {
                    money.setTextColor(getResources().getColor(R.color.green));
                } else {
                    money.setTextColor(getResources().getColor(R.color.red));
                }

                RelativeLayout.LayoutParams tutarParamRules = (RelativeLayout.LayoutParams) money.getLayoutParams();
                tutarParamRules.addRule(RelativeLayout.LEFT_OF,tıklama.getId());
                tutarParamRules.addRule(RelativeLayout.CENTER_IN_PARENT);

                //ImageViewTL
                RelativeLayout.LayoutParams imageTLParam = new RelativeLayout.LayoutParams(50, 50);
                ImageView imageTL = new ImageView(this);
                imageTL.setLayoutParams(imageTLParam);
                //imageTL.setId(R.id.userpanel_TLImage);
                if (alacakORborc) {
                    imageTL.setBackgroundResource(R.drawable.lira_yesil);
                } else {
                    imageTL.setBackgroundResource(R.drawable.lira_red);
                }

                RelativeLayout.LayoutParams imageTLParamRules = (RelativeLayout.LayoutParams) imageTL.getLayoutParams();
                imageTLParamRules.addRule(RelativeLayout.LEFT_OF, money.getId());
                imageTLParamRules.addRule(RelativeLayout.CENTER_IN_PARENT);

                relativeLayout.addView(txt, txtParamRules);
                relativeLayout.addView(tarihTxt, tarihParamRules);
                relativeLayout.addView(imageTL, imageTLParamRules);
                relativeLayout.addView(money, tutarParamRules);
                relativeLayout.addView(tıklama, ucNoktaParamRules);
                hareketlerCreator.addView(relativeLayout);
            }

            bakiyeHesapla();
        }
    }
    private void bakiyeHesapla(){
        float toplamAlacak = 0;
        float toplamBorc= 0;

        for(int i=0;i<tutarlarAlacak.size();i++){
            toplamAlacak+=tutarlarAlacak.get(i);
        }
        for(int i=0;i<tutarlarBorc.size();i++){
            toplamBorc+=tutarlarBorc.get(i);
        }

        float toplam = toplamAlacak-toplamBorc;

        TextView bakiyeText =(TextView) findViewById(R.id.bakiyeText);
        ImageView tl =(ImageView) findViewById(R.id.userpanel_simgeTL);
        TextView alacakText =(TextView) findViewById(R.id.userPanelAlacakToplami);
        TextView borcText =(TextView) findViewById(R.id.userPanelBorcToplami);
        if(toplam < 0){
            bakiyeText.setTextColor(getResources().getColor(R.color.red));
            tl.setBackgroundResource(R.drawable.lira_red);
        }
        else if(toplam >= 0) {
            bakiyeText.setTextColor(getResources().getColor(R.color.green));
            tl.setBackgroundResource(R.drawable.lira_yesil);
        }
        bakiyeText.setText(toplam + "");
        alacakText.setText(toplamAlacak + "");
        borcText.setText(toplamBorc + "");
    }
    boolean acikORkapali;
    public void bakiyeGoster(View view) {
        ImageButton ok = (ImageButton) findViewById(R.id.yukariORasagiOk);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.borcORalacakToplamlari);
        if(acikORkapali){
            Animation a = AnimationUtils.loadAnimation(this,R.anim.bakiye_kapanma);
            rl.startAnimation(a);
            ok.setBackgroundResource(R.drawable.userpanel_yukari);
            acikORkapali=false;
        }
        else{
            rl.setVisibility(View.INVISIBLE);
            Animation a = AnimationUtils.loadAnimation(this,R.anim.bakiye_acilma);
            rl.startAnimation(a);
            ok.setBackgroundResource(R.drawable.userpanel_asagi);
            acikORkapali=true;
        }
    }

    public void hareketEkle(View view) {
        startActivity(new Intent(UserPanelActivity.this, HareketAddActivity.class));
        finish();
    }

    public void geriGit(View view) {
        startActivity(new Intent(UserPanelActivity.this, Kisiler_Ekrani.class));
        finish();
    }

    public void musteriEditOrDelete(View view,String code) {
        this.code = code;
        PopupMenu popup = new PopupMenu(this,view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }
    String code;
    @Override
    public boolean onMenuItemClick(MenuItem item){
        SharedPreferences musteriler = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = musteriler.edit();

        switch (item.getItemId()){
            case R.id.item1:
                DuzenleActivity.islemCode = code;
                DuzenleActivity.activeCode = activeCode;
                startActivity(new Intent(UserPanelActivity.this,DuzenleActivity.class));
                finish();
                return true;
            case R.id.item2:
                editor.putBoolean("musteri_" + activeCode + "_islem_" + code + "_isdeleted", true);
                musteriler.edit().remove("musteri_" + activeCode + "_islem_" + code + "_alacakORborc").commit();
                musteriler.edit().remove("musteri_" + activeCode + "_islem_" + code + "_aciklama").commit();
                musteriler.edit().remove("musteri_" + activeCode + "_islem_" + code + "_tutar").commit();
                musteriler.edit().remove("musteri_" + activeCode + "_islem_" + code + "_tarih").commit();
                startActivity(new Intent(UserPanelActivity.this,UserPanelActivity.class));
                editor.commit();
                return true;
            default:
                return false;
        }
    }
}/*        ArrayList<TextView> filteredList=new ArrayList<>();
        ArrayList<RelativeLayout> filteredListLayout=new ArrayList<>();

        for(int i=0;i<itemsList.size();i++){
            RelativeLayout a = itemsListLayout.get(i);
            TextView b = itemsList.get(i);
            if(b.getText().toString().toLowerCase().contains(s.toLowerCase())){
                filteredList.add(b);
                filteredListLayout.add(a);
            }
        }
        /*for(TextView item: itemsList){
            if(item.getText().toString().toLowerCase().contains(s.toLowerCase())){
                filteredList.add(item);
            }
        }//
        for (RelativeLayout a : itemsListLayout) {
                a.setVisibility(View.GONE);
                }
                for (RelativeLayout a : filteredListLayout) {
                a.setVisibility(View.VISIBLE);
                }

*/