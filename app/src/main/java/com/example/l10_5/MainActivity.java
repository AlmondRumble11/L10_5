package com.example.l10_5;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Build;
import android.os.Bundle;


import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button haku;
    private int indeksi = 0; // listan indeksi
    private int testeri_ala = 0; // ala rajan testaus ewttä ei mene yli 10, tosin se ei tule koskaan menemään kun päätyy aina viimeiselle sivulle
    private int testeri_yla = 0; //ylä rajan testaus ewttä ei mene yli 10
    private int on_painettu = 0; // jos on menty taaksepäin ja ollaan tehty haku. Laittaa taas päälle onPageFinished, jotta listaan voidaan lisätä urlja
    private EditText url;
    private String osoite = "";
    private String aikasempi_osoite;
    private String aikasempi = "https://www.google.com/?gws_rd=ssl";
    private WebView web;
    //private String edellinen_sivu;
    //private String seuraava_sivu;
    private List<String> edellinen = new ArrayList<String>(20);
    private int koko;
    private int ticket;
    private int ticket2 = 0;
    private int ticket3 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        haku = (Button)findViewById(R.id.button);
        url = (EditText)findViewById(R.id.editText);
        web = (WebView)findViewById(R.id.webview);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); // ei näppäimistö nosta sivua
        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl("https://www.google.com/?gws_rd=ssl");
        edellinen.add("https://www.google.com/?gws_rd=ssl");


        //enter toimii hakunappina
        url.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if ((keyEvent != null ) || (i == EditorInfo.IME_ACTION_DONE)) {

                    osoite = url.getText().toString();
                    web.loadUrl("http://"+osoite);
                    haku.performClick();
                }
                return false;
            }
        });
        web.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String webUrl = web.getUrl();
                System.out.println("\n\nListan koko: "+edellinen.size()+ " ja indeksi on:  "+indeksi+"\n\n\n");

                if (webUrl.equals(aikasempi)){
                        System.out.println(aikasempi);
                }else {

                    aikasempi = webUrl;
                    //System.out.println("url on: "+ webUrl);
                    if ((ticket3 == 0) && (indeksi < edellinen.size())) {
                        edellinen.add(webUrl);
                        indeksi++;
                    }else{
                        aikasempi_osoite = osoite;
                    }

                    /*for (int b=0;b<edellinen.size();b++){
                        System.out.println("numerot: "+b+" ja url: "+edellinen.get(b));
                    }*/
                }
                for (int b=0;b<edellinen.size();b++){
                    System.out.println("numerot: "+b+" ja url: "+edellinen.get(b));
                }

            }
        });

    }
    public void hae(View v){
        System.out.println("Indeksi on "+indeksi);
        System.out.println("Listankoko on "+edellinen.size());
        if ((indeksi+1) < edellinen.size()){
            indeksi++;
            koko = edellinen.size();
            Iterator iter = edellinen.iterator();
            for (int o=indeksi+1;o<=koko;o++){
                //System.out.println(edellinen.get(o));
                System.out.println(edellinen.size()-1);
                System.out.println("Listankoko on "+edellinen.size());
                edellinen.remove(edellinen.size()-1);
            }
            testeri_ala = 0;
            testeri_yla = 0;
        }
            if (osoite.equals("index.html")) {
                /*if (edellinen.size() > 1){
                    indeksi++;
                }
                edellinen_sivu = "file:///android_asset/index.html";
                //edellinen.add("file:///android_asset/index.html");*/
            }else {

                //edellinen.add(web.getUrl());
            }
        System.out.println(edellinen);
        if (on_painettu == 1) {
            ticket3 = 0;
            ticket = 1;
            edellinen.add("http://"+osoite);
        }
        osoite = url.getText().toString();
        aikasempi_osoite = "https://www."+osoite;
        web.loadUrl("http://"+osoite);
        url.setText("");
        if (osoite.equals("index.html")) {
            web.loadUrl("file:///android_asset/index.html");
        }
        ticket2 = 0;
        //sulje näppäimistö
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) { //tarttee try...catch koska kaatuu muuuten jos ei ole näppis auki ja painaa hakunappia
            System.out.println("oopps");
        }
        for (int b=0;b<edellinen.size();b++){
            System.out.println(edellinen.get(b));
        }
    }
    public void refresh(View v){
        web.reload();
    }


    public void edellinen(View v){
        /*if (web.canGoBack()) {
            web.goBack();

        }*/
        System.out.println(indeksi);
        if (testeri_ala == 10){
            Toast.makeText(getApplicationContext(), "You can only go back 10 pages", Toast.LENGTH_SHORT).show();
        }else {
            if (indeksi > 0) {

                if ((edellinen.size() > 1) && (indeksi > 0)) {
                    indeksi--;

                }
            web.loadUrl(edellinen.get(indeksi));
            on_painettu = 1;
            testeri_ala ++;
            testeri_yla --;

            ticket2 = 1;
            ticket3 = 1;
            } else {
                Toast.makeText(getApplicationContext(), "No last page found", Toast.LENGTH_SHORT).show();
            }
        }

    }
    public void seuraava(View v){
        /*if (web.canGoForward()) {
            web.goForward();
        }*/
        if (testeri_yla == 10){
            Toast.makeText(getApplicationContext(), "You can only go forward 10 pages", Toast.LENGTH_SHORT).show();
        }else {
            System.out.println("ticket: " + ticket2 + " indeksi: " + indeksi + " osoite: " + osoite + " koko: " + edellinen.size());
            if ((ticket2 == 1) && ((indeksi + 1) < edellinen.size()) && (osoite.isEmpty() == false)) {
                web.loadUrl(edellinen.get(indeksi + 1));
                testeri_ala--;
                testeri_yla++;
                ticket3 = 1;
                indeksi++;
                for (int b = 0; b < edellinen.size(); b++) {
                    System.out.println(edellinen.get(b));
                }
            } else {
                Toast.makeText(getApplicationContext(), "No next page found", Toast.LENGTH_SHORT).show();
            }
        }
    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void muuta(View v){
        if (osoite.equals("index.html")) {
            //web.loadUrl("file:///android_asset/index.html");
            web.evaluateJavascript("javascript:initialize()", null);
        }else{
            Toast.makeText(getApplicationContext(),"You have to be on 'index.html' page",Toast.LENGTH_SHORT).show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void jskutsu(View v){
        if (osoite.equals("index.html")) {
            //web.loadUrl("file:///android_asset/index.html");
            web.evaluateJavascript("javascript:shoutOut()", null);
        }else{
            Toast.makeText(getApplicationContext(),"You have to be on 'index.html' page",Toast.LENGTH_SHORT).show();
        }
    }

}
