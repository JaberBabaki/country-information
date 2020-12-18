package com.jaber.country;

import java.io.File;
import java.util.ArrayList;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ActivityEurope extends ActivityEnhanced {

    public ArrayList<StructCountry> countrys   = new ArrayList<StructCountry>();
    public ArrayList<StructMessage> messages   = new ArrayList<StructMessage>();
    public ArrayList<StructProduct> products   = new ArrayList<StructProduct>();
    public ArrayAdapter             adapter;
    public SQLiteDatabase           newDb;
    public int                      numberOfImages;
    public File[]                   files;
    public int                      i          = 0;
    public static boolean           test       = true;
    public static int               p;
    public int                      u          = 0;
    public String[]                 continent  = { "Europe", "Asia", "Africa", "Americas", "Oceania" };
    public String[]                 rangHeader = { "#0099cc", "#ff9500", "#333333", "#f40d00", "#669900" };
    public String[]                 rangMain   = { "#33b5e5", "#ffcc00", "#555555", "#ff3b30", "#99cc00" };
    public String[]                 textMain   = { "اروپا", "آسیا", "آفریقا", "آمریکا", "اقیانوسیه" };
    public int[]                    drawable   = { R.drawable.map_blue, R.drawable.map_yellow, R.drawable.map_gray, R.drawable.map_red, R.drawable.map_green };
    public static RelativeLayout    imgE;
    public static LinearLayout      layE;
    public static LinearLayout      layToolsEuropa;

    public static ImageView         imgMapEuropa;
    public static ImageView         imgDataEuropa;
    public static ImageView         imgStaticEuropa;

    public InputMethodManager       imm;


    //public int                      countManger;
    @Override
    protected void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_root);
        //  addNotification();
        sumEuropa = 0;
        setFlagMessage();
        setFlagProduct();
        // Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/DroidNaskh.ttf");
        Intent intent = new Intent(this, InternetRecive.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //G.alarmManger.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 3000, 20000, pendingIntent);
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final EditText edtSearch = (EditText) findViewById(R.id.edtSearch);
        edtSearch.setFocusable(false);
        final LinearLayout layMessage = (LinearLayout) findViewById(R.id.layMessage);
        final LinearLayout layProduction = (LinearLayout) findViewById(R.id.layProduction);
        final LinearLayout layFriend = (LinearLayout) findViewById(R.id.layFriend);
        final LinearLayout layAbout = (LinearLayout) findViewById(R.id.layAbout);
        final LinearLayout layRsourse = (LinearLayout) findViewById(R.id.layRsourse);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        edtSearch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                edtSearch.setFocusable(true);
                edtSearch.requestFocusFromTouch();

                if (edtSearch.isFocused()) {

                    if (imm != null) {
                        imm.showSoftInput(edtSearch, 0);

                    }
                }
                /*      edtSearch.clearFocus();
                      edtSearch.setFocusable(true);
                      edtSearch.setSelected(true);*/
                //Toast.makeText(G.context, "hkhkh", Toast.LENGTH_LONG).show();
            }
        });
        layMessage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(G.currentActivity, Message.class);
                //G.currentActivity.finish();
                G.currentActivity.startActivity(intent);
            }
        });
        layFriend.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(G.currentActivity, Message.class);
                //G.currentActivity.finish();
                G.currentActivity.startActivity(intent);
            }
        });
        layProduction.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(G.currentActivity, Product.class);
                //G.currentActivity.finish();
                G.currentActivity.startActivity(intent);
            }
        });
        layAbout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(G.currentActivity, About.class);
                //G.currentActivity.finish();
                G.currentActivity.startActivity(intent);
            }
        });
        layRsourse.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(G.currentActivity, Resourse.class);
                //G.currentActivity.finish();
                G.currentActivity.startActivity(intent);
            }
        });
        final ListView lstContent = (ListView) findViewById(R.id.lstContent);
        final TextView txtNameConti = (TextView) findViewById(R.id.txtNameConti);
        final TextView txtNameContinent = (TextView) findViewById(R.id.txtNameContinent);
        txtSumEuropa = (TextView) findViewById(R.id.txtSumEuropa);
        ImageView imgGoNextEurope = (ImageView) findViewById(R.id.imgGoNextEurope);
        ImageView imgBackEurope = (ImageView) findViewById(R.id.imgBackEurope);
        imgDataEuropa = (ImageView) findViewById(R.id.imgDataEuropa);
        imgStaticEuropa = (ImageView) findViewById(R.id.imgStaticEuropa);
        final ImageView imgMainEuropa = (ImageView) findViewById(R.id.imgMainEuropa);
        imgE = (RelativeLayout) findViewById(R.id.imgE);
        imgMapEuropa = (ImageView) findViewById(R.id.imgMapEuropa);

        layToolsEuropa = (LinearLayout) findViewById(R.id.layToolsEuropa);
        final LinearLayout layHeader = (LinearLayout) findViewById(R.id.layHeader);
        layE = (LinearLayout) findViewById(R.id.layE);
        final PageIndicator indicator = (PageIndicator) findViewById(R.id.indicatorEuropa);
        final ArrayList<Bitmap> imageIds = new ArrayList<Bitmap>();
        txtNameConti.setTypeface(G.font);
        adapter = new AdapterCountry(countrys);
        lstContent.setAdapter(adapter);
        Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        lstContent.setLayoutAnimation(new LayoutAnimationController(animation));
        imgGoNextEurope.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                cancel();
                if (u == 4) {
                    u = -1;
                }
                u = u + 1;
                AdapterCountry.nameTable = continent[u];
                AdapterCountry.setRang = rangHeader[u];
                layHeader.setBackgroundColor(Color.parseColor(rangHeader[u]));
                layE.setBackgroundColor(Color.parseColor(rangMain[u]));
                txtNameConti.setText(textMain[u]);
                txtNameContinent.setText(continent[u]);
                sumEuropa = 0;
                newDb = SQLiteDatabase.openDatabase(G.DIR_DB + G.DB, null, SQLiteDatabase.OPEN_READWRITE);
                Cursor cursor112 = newDb.rawQuery("SELECT * FROM " + continent[u], null);
                countrys.clear();
                while (cursor112.moveToNext()) {
                    StructCountry martyr = new StructCountry();
                    martyr.id = cursor112.getInt(cursor112.getColumnIndex("id"));
                    martyr.name = cursor112.getString(cursor112.getColumnIndex("NA_country"));
                    martyr.likeLocal = cursor112.getInt(cursor112.getColumnIndex("LIKE"));
                    martyr.imgLike = cursor112.getInt(cursor112.getColumnIndex("Like_local"));
                    sumEuropa = sumEuropa + martyr.likeLocal;
                    countrys.add(martyr);
                }
                cursor112.close();
                newDb.close();
                txtSumEuropa.setText("" + sumEuropa);
                adapter.notifyDataSetChanged();
                Log.i("LOG", "" + u);

            }
        });

        imgBackEurope.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                cancel();
                if (u == 0) {
                    u = continent.length;
                }
                AdapterCountry.nameTable = continent[u - 1];
                AdapterCountry.setRang = rangHeader[u - 1];
                layHeader.setBackgroundColor(Color.parseColor(rangHeader[u - 1]));
                layE.setBackgroundColor(Color.parseColor(rangMain[u - 1]));
                txtNameConti.setText(textMain[u - 1]);
                txtNameContinent.setText(continent[u - 1]);
                sumEuropa = 0;

                newDb = SQLiteDatabase.openDatabase(G.DIR_DB + G.DB, null, SQLiteDatabase.OPEN_READWRITE);
                Cursor cursor11 = newDb.rawQuery("SELECT * FROM " + continent[u - 1], null);
                countrys.clear();
                while (cursor11.moveToNext()) {
                    StructCountry martyr = new StructCountry();
                    martyr.id = cursor11.getInt(cursor11.getColumnIndex("id"));
                    martyr.name = cursor11.getString(cursor11.getColumnIndex("NA_country"));
                    martyr.likeLocal = cursor11.getInt(cursor11.getColumnIndex("LIKE"));
                    martyr.imgLike = cursor11.getInt(cursor11.getColumnIndex("Like_local"));
                    sumEuropa = sumEuropa + martyr.likeLocal;
                    countrys.add(martyr);
                }
                cursor11.close();
                newDb.close();
                adapter.notifyDataSetChanged();
                txtSumEuropa.setText("" + sumEuropa);
                u = u - 1;
            }
        });
        imgDataEuropa.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                final Dialog dialog2 = new Dialog(G.currentActivity);
                dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog2.setContentView(R.layout.dialog_message);
                Window window = dialog2.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();

                wlp.gravity = Gravity.CENTER;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                window.setAttributes(wlp);
                TextView txtTextMessage = (TextView) dialog2.findViewById(R.id.txtTextMessage);
                Button btn_back = (Button) dialog2.findViewById(R.id.btn_back);
                txtTextMessage.setLineSpacing(1, 2);
                dialog2.setCancelable(false);
                dialog2.show();
                /*
                Intent intent = new Intent(G.currentActivity, ActivityData.class);
                cancel();
                G.currentActivity.startActivity(intent);*/
            }
        });
        imgStaticEuropa.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(G.currentActivity, ActivityData.class);
                intent.putExtra("static", R.drawable.sta);
                cancel();
                G.currentActivity.startActivity(intent);
            }
        });
        imgMapEuropa.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (p == 0) {
                    layE.setVisibility(View.INVISIBLE);
                    imgE.setVisibility(View.VISIBLE);
                    layToolsEuropa.setBackgroundColor(Color.parseColor("#80020d10"));
                    imgMapEuropa.setImageResource(drawable[u]);
                    imgDataEuropa.setImageResource(R.drawable.datae);
                    imgStaticEuropa.setImageResource(R.drawable.sta);
                    test = true;
                    p = 1;
                    imageIds.clear();
                    //******
                    File dir = new File(G.DIR_CONTINENT + continent[u]);
                    if (dir.exists()) {
                        files = dir.listFiles();
                        numberOfImages = files.length;

                        for (int i = 0; i < files.length; i++) {
                            Bitmap bitmap = BitmapFactory.decodeFile("" + files[i]);
                            imageIds.add(bitmap);
                        }

                        if (files.length > 0) {
                            indicator.setIndicatorsCount(numberOfImages);
                            imgMainEuropa.setImageBitmap(imageIds.get(0));
                            i = 1;
                            (new Thread(new Runnable()
                            {

                                @Override
                                public void run()
                                {
                                    while ( !Thread.interrupted() && test)
                                        try
                                        {

                                            Thread.sleep(3000);

                                            runOnUiThread(new Runnable() // start actions in UI thread
                                            {

                                                @Override
                                                public void run()
                                                {

                                                    // imgMain.startAnimationothis,android.R.ut);
                                                    indicator.setCurrentPage(i);
                                                    ImageViewAnimatedChange(G.context, imgMainEuropa, imageIds.get(i));
                                                    //  imgMainEuropa.setImageBitmap(imageIds.get(i));
                                                    Log.i("LOG", "Percent jaber: " + i);
                                                    i = i + 1;
                                                    if (i == numberOfImages) {
                                                        i = 0;
                                                    }
                                                }
                                            });
                                        }
                                        catch (InterruptedException e)
                                        {}
                                }
                            })).start();

                            imgMainEuropa.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                    final Dialog dialog3 = new Dialog(ActivityEurope.this);
                                    dialog3.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                                    dialog3.setContentView(R.layout.dialog_zoom);
                                    // final ImageView laypi = (ImageView) dialog3.findViewById(R.id.touchImage);
                                    Log.i("LOG", "Pe   " + i);
                                    int r = i;
                                    if (r == 0) {
                                        r = numberOfImages;
                                    }
                                    //  ZoomableImageView laypi = (ZoomableImageView) dialog3.findViewById(R.id.IMAGEID);
                                    // touch.setImageBitmap(bitmap);
                                    //  laypi.setImageBitmap(imageIds.get(r - 1));
                                    dialog3.setCancelable(true);
                                    dialog3.setCanceledOnTouchOutside(true);
                                    dialog3.show();
                                }
                            });
                        }
                    }
                    ///*****
                }
                else {
                    cancel();
                }
            }
        });

        AdapterCountry.nameTable = continent[0];
        AdapterCountry.setRang = rangHeader[0];
        layHeader.setBackgroundColor(Color.parseColor(rangHeader[0]));
        layE.setBackgroundColor(Color.parseColor(rangMain[0]));
        txtNameConti.setText(textMain[0]);
        txtNameContinent.setText(continent[0]);

        newDb = SQLiteDatabase.openDatabase(G.DIR_DB + G.DB, null, SQLiteDatabase.OPEN_READWRITE);
        Cursor cursor112 = newDb.rawQuery("SELECT * FROM " + continent[0], null);
        countrys.clear();
        while (cursor112.moveToNext()) {
            StructCountry martyr = new StructCountry();
            martyr.id = cursor112.getInt(cursor112.getColumnIndex("id"));
            martyr.name = cursor112.getString(cursor112.getColumnIndex("NA_country"));
            martyr.likeLocal = cursor112.getInt(cursor112.getColumnIndex("LIKE"));
            martyr.imgLike = cursor112.getInt(cursor112.getColumnIndex("Like_local"));
            sumEuropa = sumEuropa + martyr.likeLocal;
            countrys.add(martyr);
        }
        cursor112.close();
        newDb.close();
        txtSumEuropa.setText("" + sumEuropa);
        adapter.notifyDataSetChanged();

    }


    private void setFlagMessage() {
        newDb = SQLiteDatabase.openDatabase(G.DIR_DB + G.DB, null, SQLiteDatabase.OPEN_READWRITE);
        Cursor cursor1112 = newDb.rawQuery("SELECT * FROM Message", null);
        countrys.clear();
        while (cursor1112.moveToNext()) {
            StructMessage message = new StructMessage();
            message.id = cursor1112.getInt(cursor1112.getColumnIndex("id"));
            message.title = cursor1112.getString(cursor1112.getColumnIndex("title"));
            message.text = cursor1112.getString(cursor1112.getColumnIndex("text"));
            message.date = cursor1112.getString(cursor1112.getColumnIndex("date"));
            message.readOnRead = cursor1112.getInt(cursor1112.getColumnIndex("flag"));
            messages.add(message);
        }
        for (final StructMessage task1: messages) {
            int t = task1.readOnRead;
            if (t == 0) {
                final Dialog dialog2 = new Dialog(ActivityEurope.this);
                dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog2.setContentView(R.layout.dialog_message);
                Window window = dialog2.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                window.setAttributes(wlp);
                CustomTextView txtTextMessage = (CustomTextView) dialog2.findViewById(R.id.txtTextMessage);
                CustomTextView txtDateMainDialog = (CustomTextView) dialog2.findViewById(R.id.txtDateMainDialog);
                Button btnListMessage = (Button) dialog2.findViewById(R.id.btnListMessage);
                Button btnBackMessage = (Button) dialog2.findViewById(R.id.btnBackMessage);
                btnBackMessage.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        newDb = SQLiteDatabase.openDatabase(G.DIR_DB + G.DB, null, SQLiteDatabase.OPEN_READWRITE);
                        newDb.execSQL("UPDATE Message SET flag  = 1  where  id =" + task1.id);
                        dialog2.dismiss();
                    }
                });
                btnListMessage.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Log.i("LOG", "" + task1.id);
                        Intent intent = new Intent(G.currentActivity, Message.class);
                        G.currentActivity.startActivity(intent);
                        newDb = SQLiteDatabase.openDatabase(G.DIR_DB + G.DB, null, SQLiteDatabase.OPEN_READWRITE);
                        newDb.execSQL("UPDATE Message SET flag  = 1  where  id =" + task1.id);
                        dialog2.dismiss();
                    }
                });
                txtTextMessage.setText(task1.text);
                txtDateMainDialog.setText(task1.date);
                txtTextMessage.setLineSpacing(1, 2);
                dialog2.setCancelable(true);
                dialog2.show();
            }

        }
        cursor1112.close();
        newDb.close();
    }


    private void setFlagProduct() {
        newDb = SQLiteDatabase.openDatabase(G.DIR_DB + G.DB, null, SQLiteDatabase.OPEN_READWRITE);
        Cursor cursor1112 = newDb.rawQuery("SELECT * FROM Product", null);
        products.clear();
        while (cursor1112.moveToNext()) {
            StructProduct product = new StructProduct();
            product.id = cursor1112.getInt(cursor1112.getColumnIndex("id"));
            product.title = cursor1112.getString(cursor1112.getColumnIndex("title"));
            product.text = cursor1112.getString(cursor1112.getColumnIndex("text"));
            product.pack = cursor1112.getString(cursor1112.getColumnIndex("pack"));
            product.icon = cursor1112.getString(cursor1112.getColumnIndex("icon"));
            product.readOnRead = cursor1112.getInt(cursor1112.getColumnIndex("flag"));
            products.add(product);
        }
        for (final StructProduct task1: products) {
            int t = task1.readOnRead;
            if (t == 0) {
                final Dialog dialog3 = new Dialog(ActivityEurope.this);
                dialog3.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog3.setContentView(R.layout.dialog_product);
                Window window = dialog3.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                window.setAttributes(wlp);
                ImageView imgLogoProduct = (ImageView) dialog3.findViewById(R.id.imgLogoProduct);
                CustomTextView txtTextProduct = (CustomTextView) dialog3.findViewById(R.id.txtTextProduct);
                CustomTextView txtTitleProduct = (CustomTextView) dialog3.findViewById(R.id.txtTitleProduct);
                Button btnListMessageProduct = (Button) dialog3.findViewById(R.id.btnListMessageProduct);
                Button btnbackProduct = (Button) dialog3.findViewById(R.id.btnbackProduct);
                btnbackProduct.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        newDb = SQLiteDatabase.openDatabase(G.DIR_DB + G.DB, null, SQLiteDatabase.OPEN_READWRITE);
                        newDb.execSQL("UPDATE Product SET flag  = 1  where  id =" + task1.id);
                        dialog3.dismiss();
                    }
                });
                btnListMessageProduct.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Log.i("LOG", "" + task1.id);
                        Intent intent = new Intent(G.currentActivity, Product.class);
                        G.currentActivity.startActivity(intent);
                        newDb = SQLiteDatabase.openDatabase(G.DIR_DB + G.DB, null, SQLiteDatabase.OPEN_READWRITE);
                        newDb.execSQL("UPDATE Product SET flag  = 1  where  id =" + task1.id);
                        dialog3.dismiss();
                    }
                });
                txtTextProduct.setText(task1.text);
                txtTitleProduct.setText(task1.title);
                txtTextProduct.setLineSpacing(1, 2);
                dialog3.setCancelable(true);
                dialog3.show();
            }

        }
        cursor1112.close();
        newDb.close();
    }


    public static void ImageViewAnimatedChange(Context c, final ImageView v, final Bitmap new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.slide_out_right);
        final Animation anim_in = AnimationUtils.loadAnimation(c, android.R.anim.slide_in_left);
        anim_out.setAnimationListener(new AnimationListener()
        {

            @Override
            public void onAnimationStart(Animation animation) {}


            @Override
            public void onAnimationRepeat(Animation animation) {}


            @Override
            public void onAnimationEnd(Animation animation)
            {
                v.setImageBitmap(new_image);
                anim_in.setAnimationListener(new AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {}


                    @Override
                    public void onAnimationRepeat(Animation animation) {}


                    @Override
                    public void onAnimationEnd(Animation animation) {}
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }


    public static void cancel() {
        layE.setVisibility(View.VISIBLE);
        imgE.setVisibility(View.INVISIBLE);
        layToolsEuropa.setBackgroundColor(Color.parseColor("#ffffff"));
        imgMapEuropa.setImageResource(R.drawable.flag);
        imgDataEuropa.setImageResource(R.drawable.data);
        imgStaticEuropa.setImageResource(R.drawable.piepng);
        test = false;
        p = 0;
    }


    public static boolean saveArray(ArrayList<String> array, String arrayName) {
        SharedPreferences.Editor editor = G.preferences.edit();
        editor.putInt(arrayName + "_size", array.size());
        Log.i("NET", "saveArray  : ");
        for (int i = 0; i < array.size(); i++)
            editor.putString(arrayName + "_" + i, array.get(i));
        return editor.commit();
    }


    public static void loadArray(ArrayList<String> array, String arrayName) {
        array.clear();
        int size = G.preferences.getInt(arrayName + "_size", 0);
        boolean check = G.preferences.getBoolean("CHECK", false);
        Log.i("NET", "loadArray gabl  : ");
        if (check != false) {
            for (int i = 0; i < size; i++) {
                array.add(i, G.preferences.getString(arrayName + "_" + i, null));
                Log.i("NET", "loadArray  : " + i);
            }
        }
    }


    @Override
    public void onBackPressed() {
        test = false;
        super.onBackPressed();
    }
}
