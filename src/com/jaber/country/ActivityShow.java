package com.jaber.country;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class ActivityShow extends ActivityEnhanced {

    public TextView[]                         textView         = new TextView[24];
    public SQLiteDatabase                     newDb;
    public int                                numberIndicator  = 0;
    public static int                         numberOfImages;
    public static int                         id;
    public int                                numberOfLike;
    public boolean                            exitThread       = true;
    public boolean                            exitHandelr      = true;
    public File[]                             files;
    public MediaPlayer                        mediaPlayer;
    public String                             rang;
    public String                             cat;
    public static String                      table;
    public ArrayList<Integer>                 sumSize          = new ArrayList<Integer>();
    public int[]                              percentList;
    private static ArrayList<DownloadRequest> downloadRequests = new ArrayList<DownloadRequest>();
    public ArrayList<Bitmap>                  imageIds         = new ArrayList<Bitmap>();
    public ArrayList<File>                    fileList         = new ArrayList<File>();
    public PageIndicator                      indicator;
    public static int                         sumLike;
    public static int                         nameCountry;
    public static String                      nameContinent;


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_show);
        textView[0] = (TextView) findViewById(R.id.txtNameL);
        textView[1] = (TextView) findViewById(R.id.txtCapitan);
        textView[2] = (TextView) findViewById(R.id.txtTG);
        textView[3] = (TextView) findViewById(R.id.txtLA);
        textView[4] = (TextView) findViewById(R.id.txtPOP);
        textView[5] = (TextView) findViewById(R.id.txtAR);
        textView[6] = (TextView) findViewById(R.id.txtRE);
        textView[7] = (TextView) findViewById(R.id.txtIN);
        textView[8] = (TextView) findViewById(R.id.txtEM);
        textView[9] = (TextView) findViewById(R.id.txtUN);
        textView[10] = (TextView) findViewById(R.id.txtNU);
        textView[11] = (TextView) findViewById(R.id.txtBNameFarsi);
        textView[12] = (TextView) findViewById(R.id.txtBNameLatin);
        textView[13] = (TextView) findViewById(R.id.txtBCA);
        textView[14] = (TextView) findViewById(R.id.txtBTG);
        textView[15] = (TextView) findViewById(R.id.txtBLA);
        textView[16] = (TextView) findViewById(R.id.txtBPOP);
        textView[17] = (TextView) findViewById(R.id.txtBAR);
        textView[18] = (TextView) findViewById(R.id.txtBRE);
        textView[19] = (TextView) findViewById(R.id.txtBIN);
        textView[20] = (TextView) findViewById(R.id.txtBEM);
        textView[21] = (TextView) findViewById(R.id.txtBUN);
        textView[22] = (TextView) findViewById(R.id.txtBNU);
        textView[23] = (TextView) findViewById(R.id.txtSumEuropa);
        indicator = (PageIndicator) findViewById(R.id.indicator);

        ImageView imgGal = (ImageView) findViewById(R.id.imgGal);
        ImageView imgMoney = (ImageView) findViewById(R.id.imgMoney);
        ImageView imgMap = (ImageView) findViewById(R.id.imgMap);
        final ImageView imgMain = (ImageView) findViewById(R.id.imgMain);
        final ImageView imgPlay = (ImageView) findViewById(R.id.imgPlay);
        final ImageView imgStop = (ImageView) findViewById(R.id.imgStop);
        final LinearLayout layHeader = (LinearLayout) findViewById(R.id.layHeader);

        table = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
            table = extras.getString("table");
            Log.i("LOG", "" + table);
            numberOfLike = extras.getInt("like");
        }

        textView[23].setText("" + numberOfLike);

        if (table.equals("Europe")) {
            layHeader.setBackgroundColor(Color.parseColor("#33B5E6"));
            rang = "#33B5E6";
        } else if (table.equals("Africa")) {
            layHeader.setBackgroundColor(Color.parseColor("#000000"));
            rang = "#000000";
        } else if (table.equals("Asia")) {
            layHeader.setBackgroundColor(Color.parseColor("#ff9500"));
            rang = "#ff9500";

        } else if (table.equals("Americas")) {
            layHeader.setBackgroundColor(Color.parseColor("#f40d00"));
            rang = "#f40d00";

        } else if (table.equals("Oceania")) {
            layHeader.setBackgroundColor(Color.parseColor("#669900"));
            rang = "#669900";
        }

        for (int i = 12; i < 23; i++) {
            textView[i].setTextColor(Color.parseColor(rang));
        }

        newDb = SQLiteDatabase.openDatabase(G.DIR_DB + G.DB, null, SQLiteDatabase.OPEN_READWRITE);
        Cursor cursor = newDb.rawQuery("SELECT * FROM '" + table + "' where id =" + id, null);
        cursor.moveToNext();
        int idd = cursor.getInt(cursor.getColumnIndex("id"));
        Log.i("LOG", "lll  :  " + idd);
        textView[11].setText(cursor.getString(cursor.getColumnIndex("NA_country")));
        textView[12].setText(cursor.getString(cursor.getColumnIndex("LN_country")));
        textView[13].setText(cursor.getString(cursor.getColumnIndex("CA_country")));
        textView[14].setText(cursor.getString(cursor.getColumnIndex("TG_country")));
        textView[15].setText(cursor.getString(cursor.getColumnIndex("LA_country")));
        textView[16].setText(cursor.getString(cursor.getColumnIndex("POP_country")));
        textView[17].setText(cursor.getString(cursor.getColumnIndex("AR_country")));
        textView[18].setText(cursor.getString(cursor.getColumnIndex("RE_country")));
        textView[19].setText(cursor.getString(cursor.getColumnIndex("IN_country")));
        textView[20].setText(cursor.getString(cursor.getColumnIndex("EM_country")));
        textView[21].setText(cursor.getString(cursor.getColumnIndex("UN_country")));
        textView[22].setText(cursor.getString(cursor.getColumnIndex("NU_country")));
        cursor.close();
        newDb.close();

        mediaPlayer = new MediaPlayer();
        imgPlay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                File dir2 = new File(G.DIR_SOUND + table + "/" + id + "/");

                if (dir2.exists()) {

                    try {
                        mediaPlayer.setDataSource(G.DIR_SOUND + table + "/" + id + "/" + table + id);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    }
                    catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                    catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    imgPlay.setVisibility(View.GONE);
                    imgStop.setVisibility(View.VISIBLE);
                } else {
                    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo netInfo = cm.getActiveNetworkInfo();
                    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                        String conti = table + "/" + id + "/";
                        String des = G.DIR_SOUND + "/" + table + "/" + id + "/";
                        String http = "http://" + G.HTTP + "/country-birth/sound/" + conti;
                        resDownloder(http + table + id, des, table + id);
                    }
                }
            }
        });

        imgStop.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                imgStop.setVisibility(View.GONE);
                imgPlay.setVisibility(View.VISIBLE);
            }
        });

        imageIds.clear();

        final RotateAnimation anim = new RotateAnimation(0f, 350f, 15f, 15f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(700);

        File dir = new File(G.DIR_PHOTO + table + "/" + id);
        if (dir.exists()) {
            fileList.clear();
            getSumOfPhoto();
            numberOfImages = fileList.size();
            setBitmapToImageView();

            imgMain.setImageBitmap(imageIds.get(0));
            if (numberOfImages > 1) {
                indicator.setIndicatorsCount(numberOfImages);
            }
            numberIndicator = 1;
            (new Thread(new Runnable()
            {

                @Override
                public void run()
                {
                    while ( !Thread.interrupted() && exitThread)
                        try
                        {

                            Thread.sleep(3000);

                            runOnUiThread(new Runnable()
                            {

                                @Override
                                public void run()
                                {
                                    if (imageIds.size() > 1) {
                                        indicator.setCurrentPage(numberIndicator);
                                        ImageViewAnimatedChange(G.context, imgMain, imageIds.get(numberIndicator));
                                        numberIndicator = numberIndicator + 1;
                                        if (numberIndicator == numberOfImages) {
                                            numberIndicator = 0;
                                        }
                                    }
                                }
                            });
                        }
                        catch (InterruptedException e)
                        {}
                }
            })).start();

        }

        imgMain.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                final Dialog dialog3 = new Dialog(ActivityShow.this);
                dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                dialog3.setContentView(R.layout.dialog_zoom);
                final ImageView rr = (ImageView) dialog3.findViewById(R.id.r);
                Log.i("LOG", "Pe   " + numberIndicator);
                int r = numberIndicator;
                if (r == 0) {
                    r = numberOfImages;
                }

                rr.setImageBitmap(imageIds.get(r - 1));
                rr.setOnTouchListener(new Touch());
                dialog3.setCancelable(true);
                dialog3.setCanceledOnTouchOutside(true);
                dialog3.show();
            }
        });

        imgGal.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // cat = "1";
                downloader();
            }
        });

        imgMoney.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //cat = "2";
                //downloader(cat);
            }
        });

        imgMap.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //cat = "3";
                // downloader(cat);
            }
        });
    }


    /*
     * function  relating to the above code
     */

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


    private void updateDownloadStatus() {

        for (int i = 0; i < downloadRequests.size(); i++) {
            DownloadRequest request = downloadRequests.get(i);
            int percent = request.getPercent();
            percentList[i] = percent;
        }
    }


    private void downloader() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            Log.i("LOG", "sendData2 ghabl");
            sendData2(id, table);
            Log.i("LOG", "sendData2 baid");
            exitHandelr = true;
            percentList = new int[downloadRequests.size()];

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    while (exitHandelr) {
                        try {
                            Thread.sleep(30);

                            G.HANDLER.post(new Runnable() {

                                @Override
                                public void run() {

                                    updateDownloadStatus();
                                    int f = 100;
                                    for (int i = 0; i < percentList.length; i++) {
                                        if (f > percentList[i]) {

                                            f = percentList[i];
                                        }

                                    }
                                    if (f >= 100) {
                                        exitHandelr = false;
                                        fileList.clear();
                                        imageIds.clear();
                                        getSumOfPhoto();
                                        int w = numberOfImages;
                                        numberOfImages = fileList.size();
                                        indicator.setIndicatorsCount(numberOfImages);
                                        if (numberOfImages > w) {
                                            Toast.makeText(G.context, "دانلود به اتمام رسیده ", Toast.LENGTH_LONG).show();
                                        }
                                        setBitmapToImageView();

                                    }

                                }
                            });
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            thread.start();

        } else {
            final Dialog dialog2 = new Dialog(ActivityShow.this);
            dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog2.setContentView(R.layout.dialog_conect);
            Button btn_setting = (Button) dialog2.findViewById(R.id.btn_setting);
            Button btn_back = (Button) dialog2.findViewById(R.id.btn_back);
            btn_setting.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                    startActivity(intent);
                }
            });
            btn_back.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    dialog2.dismiss();
                }
            });
            dialog2.setCancelable(false);
            dialog2.show();
        }
    }


    private void sendData2(int number, String word) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("id", "" + number));
        params.add(new BasicNameValuePair("word", "" + word));
        // params.add(new BasicNameValuePair("cat", "" + cat));
        Log.i("LOG", "Sd gabl");
        Sd(params, "getPhoto");
    }


    public static void Sp(ArrayList<NameValuePair> params) {
        String resu = Webservice.readurl("http://" + G.HTTP + "/country-birth/country.php?action=setLike", params);
    }


    public static void Sd(ArrayList<NameValuePair> params, String action) {
        String resu = Webservice.readurl("http://" + G.HTTP + "/country-birth/country.php?action=" + action, params);
        if (resu != null) {
            String conti = table + "/" + id + "/";
            String dir = G.DIR_PHOTO + "/" + table + "/" + id + "/";
            String http = "http://" + G.HTTP + "/country-birth/photo/" + conti;
            try {
                JSONArray array = new JSONArray(resu);

                if ((array.length() - 2) > numberOfImages - 1) {
                    Toast.makeText(G.context, "در حال دانلود لطفا منتظر بمانید ", Toast.LENGTH_SHORT).show();
                    for (int i = 2; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String pathPhoto = object.getString("op");
                        resDownloder(http + pathPhoto, dir, pathPhoto);
                    }
                } else {
                    Toast.makeText(G.context, "منابعی برای دانلود وجود ندارد ", Toast.LENGTH_LONG).show();
                }

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    /* public static void Ss() {
         String resu = Webservice.readurl("http://192.168.1.4/country-birth/country.php?action=getLike", null);
         if (resu != null) {

             try {
                 JSONArray array = new JSONArray(resu);

                 // Toast.makeText(G.context, "در حال دانلود لطفا منتظر بمانید ", Toast.LENGTH_SHORT).show();
                 for (int i = 0; i < array.length(); i++) {
                     JSONObject object = array.getJSONObject(i);
                     sumLike = object.getInt("like_co");
                     nameCountry = object.getInt("id");
                     nameContinent = object.getString("europ");

                 }

                 //Toast.makeText(G.context, "منابعی برای دانلود وجود ندارد ", Toast.LENGTH_LONG).show();

             }
             catch (JSONException e) {
                 e.printStackTrace();
             }
         }
     }*/

    public static void resDownloder(String http, String des, String name) {

        new File(des).mkdirs();
        DownloadRequest downloadRequest = new DownloadRequest()
                .downloadPath(http)
                .filepath(des + name)
                .simulate(true)
                .download();
        if (des.contains("DIR_PHOTO")) {
            downloadRequests.add(downloadRequest);
        }
    }


    private void getSumOfPhoto() {

        File dir2 = new File(G.DIR_PHOTO + table + "/" + id + "/");
        if (dir2.exists()) {
            files = dir2.listFiles();
            for (int x = 0; x < files.length; x++) {
                fileList.add(files[x]);
            }
        }

    }


    private void setBitmapToImageView() {
        for (int i = 0; i < fileList.size(); i++) {
            Bitmap bitmap = BitmapFactory.decodeFile("" + fileList.get(i));
            //Log.i("LOG", "path : " + fileList.get(i));
            imageIds.add(bitmap);
        }

    }


    @Override
    public void onBackPressed() {
        mediaPlayer.stop();
        mediaPlayer.reset();
        exitThread = false;
        super.onBackPressed();
    }

}
