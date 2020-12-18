package com.jaber.country;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ActivityData extends Activity {

    public int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_data_europa);
        ImageView imgDataStaticEuropa = (ImageView) findViewById(R.id.imgDataStaticEuropa);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            id = extras.getInt("static");
            imgDataStaticEuropa.setImageResource(id);
        }
        final LinearLayout layData = (LinearLayout) findViewById(R.id.layData);
        TextView txtDataHeader = (TextView) findViewById(R.id.txtDataHeader);
        if (AdapterCountry.nameTable.equals("Europe")) {
            layData.setBackgroundColor(Color.parseColor("#33B5E6"));
        } else if (AdapterCountry.nameTable.equals("Africa")) {
            layData.setBackgroundColor(Color.parseColor("#000000"));
            txtDataHeader.setText("آفریقا");
        } else if (AdapterCountry.nameTable.equals("Asia")) {
            layData.setBackgroundColor(Color.parseColor("#ff9500"));
            txtDataHeader.setText("آسیا");

        } else if (AdapterCountry.nameTable.equals("Americas")) {
            layData.setBackgroundColor(Color.parseColor("#f40d00"));
            txtDataHeader.setText("آمریکا");

        } else if (AdapterCountry.nameTable.equals("Oceania")) {
            layData.setBackgroundColor(Color.parseColor("#669900"));
            txtDataHeader.setText("اقیانوسیه");
        }
    }
}
