package com.jaber.country;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;


public class Image extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);
        ImageView img = (ImageView) findViewById(R.id.imgId);
        String bit = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bit = extras.getString("id");

        }
        Bitmap bitmap = BitmapFactory.decodeFile(bit);
        img.setImageBitmap(bitmap);

    };

}
