package com.jaber.country;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class InternetRecive extends BroadcastReceiver {

    public SQLiteDatabase           newDb;
    public String                   title;
    public String                   text;
    public String                   icon;
    public int                      countManger;
    public int                      countProduct;
    public int                      id;
    public String                   date;
    public ArrayList<StructCountry> countrys = new ArrayList<StructCountry>();
    private Bitmap                  marker;


    private void addNotification(String title, String text) {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(G.currentActivity)
                        .setSmallIcon(R.drawable.message_gray)

                        .setContentTitle(title)
                        .setContentText(text);

        Intent notificationIntent = new Intent(G.currentActivity, ActivityEurope.class);
        PendingIntent contentIntent = PendingIntent.getActivity(G.context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification  
        // NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        G.manager.notify(2, builder.build());
    }


    private void addNotification(Bitmap bitmap, String title, String text) {
        Log.i("LOG", "bitmap = " + bitmap);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(G.currentActivity)
                        .setSmallIcon(R.drawable.playstor_gray)
                        .setLargeIcon(bitmap)
                        .setContentTitle(title)
                        .setContentText(text);

        Intent notificationIntent = new Intent(G.currentActivity, ActivityEurope.class);
        PendingIntent contentIntent = PendingIntent.getActivity(G.context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification  
        // NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        G.manager.notify(2, builder.build());
    }


    public void Sss(ArrayList<NameValuePair> params) {
        String resu = Webservice.readurl("http://" + G.HTTP + "/country-birth/country.php?action=getLike", params);
        if ( !resu.contains("%")) {
            try {
                JSONArray array = new JSONArray(resu);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    StructCountry martyr = new StructCountry();
                    martyr.likeLocal = object.getInt("like_co");
                    martyr.id = object.getInt("id");
                    martyr.name = object.getString("europ");
                    martyr.time = object.getString("timenow");
                    countrys.add(martyr);
                }

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void getCountMessage() {
        newDb = SQLiteDatabase.openDatabase(G.DIR_DB + G.DB, null, SQLiteDatabase.OPEN_READWRITE);
        countManger = 0;
        Cursor cursor = newDb.rawQuery("SELECT * FROM Message", null);
        while (cursor.moveToNext()) {
            countManger = cursor.getCount();
        }

        cursor.close();
        newDb.close();
    }


    private void getCountProduct() {
        newDb = SQLiteDatabase.openDatabase(G.DIR_DB + G.DB, null, SQLiteDatabase.OPEN_READWRITE);
        countProduct = 0;
        Cursor cursor = newDb.rawQuery("SELECT * FROM Product", null);
        while (cursor.moveToNext()) {
            countProduct = cursor.getCount();
        }

        cursor.close();
        newDb.close();
    }


    public void Sss() {
        String resu = Webservice.readurl("http://" + G.HTTP + "/country-birth/country.php?action=getMessage", null);
        Log.i("LOG", "1" + title + "  " + date + "  " + text);
        if ( !resu.contains("%")) {
            try {
                Log.i("LOG", "2" + title + "  " + date + "  " + text);
                JSONArray array = new JSONArray(resu);
                getCountMessage();

                Log.i("LOG", "##2##  " + array.length() + "  " + countManger);
                if (array.length() > countManger - G.COUNT_MESSAGE) {

                    for (int i = countManger - G.COUNT_MESSAGE; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        // StructCountry martyr = new StructCountry();
                        Log.i("LOG", "3" + title + "  " + date + "  " + text);
                        id = object.getInt("id");
                        title = object.getString("title");
                        text = object.getString("text");
                        date = object.getString("date");
                        newDb = SQLiteDatabase.openDatabase(G.DIR_DB + G.DB, null, SQLiteDatabase.OPEN_READWRITE);
                        int flag = 0;
                        newDb.execSQL("INSERT INTO Message (title,text,date,flag)  VALUES('" + title + "','" + text + "','" + date + "','" + flag + "')");
                        newDb.close();
                        // Bitmap bitmap = BitmapFactory.decodeFile(R.drawable.message_gray);
                        addNotification(title, text);

                    }
                }

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void Ssss() {
        String resu = Webservice.readurl("http://" + G.HTTP + "/country-birth/country.php?action=getProduct", null);
        //Log.i("LOG", "1" + title + "  " + date + "  " + text);
        if ( !resu.contains("%")) {
            try {
                JSONArray array = new JSONArray(resu);
                getCountProduct();
                Log.i("LOG", "##87##  " + array.length() + "  " + countProduct);
                if (array.length() > countProduct - 1) {

                    for (int i = countProduct - 1; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        // StructCountry martyr = new StructCountry();
                        // Log.i("LOG", "3" + title + "  " + date + "  " + text);
                        id = object.getInt("id");
                        title = object.getString("name_app");
                        date = object.getString("package");
                        text = object.getString("main_text");
                        icon = object.getString("icon");
                        ActivityShow.resDownloder("http://" + G.HTTP + "/country-birth/photo/product/" + icon, G.DIR_PRODUCT, icon);
                        newDb = SQLiteDatabase.openDatabase(G.DIR_DB + G.DB, null, SQLiteDatabase.OPEN_READWRITE);
                        int flag = 0;
                        newDb.execSQL("INSERT INTO Product (title,text,pack,icon,flag)  VALUES('" + title + "','" + text + "','" + date + "','" + icon + "','" + flag + "')");
                        newDb.close();
                        Bitmap bitmap = BitmapFactory.decodeFile(G.DIR_PRODUCT + icon);
                        //  bitmap.;
                        // BitmapDrawable d = new BitmapDrawable(G.context.getResources(), bitmap);
                        //  Integer y = Integer.decode(bitmap);//bitmap;
                        Log.i("LOG", "2" + G.DIR_PRODUCT + icon);
                        addNotification(bitmap, title, text);

                    }
                }

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void getData() {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("lastTime", "" + G.preferences.getString("TIME", "")));
        Log.i("NET", "time" + G.preferences.getString("TIME", ""));
        Sss(params);

        for (StructCountry task1: countrys) {
            //Log.i("GET", "connecte :" + task1.likeLocal + ":::::" + task1.id);
            newDb.execSQL("UPDATE Europe SET LIKE='" + task1.likeLocal + "'  where  id =" + task1.id);
            SharedPreferences.Editor editor = G.preferences.edit();
            editor.putString("TIME", task1.time);
            editor.commit();
        }
        countrys.clear();
    }


    public void getMessage() {

    }


    public void runer() {
        ActivityEurope.loadArray(G.indexList, "indexList");
        ActivityEurope.loadArray(G.tableList, "tableList");

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                newDb = SQLiteDatabase.openDatabase(G.DIR_DB + G.DB, null, SQLiteDatabase.OPEN_READWRITE);
                ConnectivityManager cm = (ConnectivityManager) G.context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getActiveNetworkInfo();

                if (netInfo != null && netInfo.isConnectedOrConnecting()) {

                    Log.i("NET", "connecte");
                    if (G.indexList.size() != 0) {
                        for (int i = 0; i < G.indexList.size(); i++) {
                            Cursor cursor112 = newDb.rawQuery("SELECT * FROM '" + G.tableList.get(i) + "' where id =" + G.indexList.get(i), null);
                            cursor112.moveToNext();
                            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                            int last = cursor112.getInt(cursor112.getColumnIndex("last"));
                            int Like_local = cursor112.getInt(cursor112.getColumnIndex("Like_local"));
                            if (Like_local != last) {
                                params.add(new BasicNameValuePair("Like_local", "" + Like_local));
                                params.add(new BasicNameValuePair("nameContinent", "" + G.tableList.get(i)));
                                params.add(new BasicNameValuePair("nameCountry", "" + G.indexList.get(i)));
                                ActivityShow.Sp(params);
                                newDb.execSQL("UPDATE '" + G.tableList.get(i) + "'SET last='" + Like_local + "'  where  id =" + G.indexList.get(i));
                            }

                        }
                        SharedPreferences.Editor editor = G.preferences.edit();
                        editor.putBoolean("CHECK", false);
                        editor.commit();
                        G.indexList.clear();
                        G.tableList.clear();
                    }
                    getData();
                    Sss();
                    Ssss();
                    Log.i("LOG", "4" + title + "  " + date + "  " + text);
                    newDb.close();
                } else {
                    Log.i("NET", "not connecte");
                }
            }
        });
        thread.start();
    }


    @Override
    public void onReceive(Context contex, Intent intent) {
        runer();
    }
}
