package com.jaber.country;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;


public class G extends Application {

    public static Context             context;
    public static Activity            currentActivity;
    public static String              currentActivity2;
    public static LayoutInflater      inflater;
    public static Typeface            font;
    public static final String        DIR_SDCARD           = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String              DIR_APP              = DIR_SDCARD + "/Birth-Country";
    public static String              DIR_DB               = DIR_APP + "/DB-Country";
    public static String              DIR_PHOTO            = DIR_APP + "/Image-Country/";
    public static String              DIR_SOUND            = DIR_APP + "/Sound-Country/";
    public static String              DIR_CONTINENT        = DIR_APP + "/Continent-Country/";
    public static String              DIR_FLAG             = DIR_APP + "/flag-cuntry/";
    public static String              DIR_PRODUCT          = DIR_APP + "/other-product/";
    public static int                 COUNT_MESSAGE        = 4;
    public static String              DB                   = "/r.sqlite";
    public static SQLiteDatabase      newDb;
    public String[]                   photoContinent       = { "Europe", "Asia", "Africa", "Americas", "Oceania" };
    public String[]                   fileList;
    public static final Handler       HANDLER              = new Handler();
    public int                        i                    = 0;
    public int                        f                    = 0;
    public int                        j                    = 0;
    public int                        s                    = 0;
    public static int                 size                 = 14;
    public static final int           DOWNLOAD_BUFFER_SIZE = 8 * 1024;
    public static AlarmManager        alarmManger;
    public static ArrayList<String>   indexList            = new ArrayList<String>();
    public static ArrayList<String>   tableList            = new ArrayList<String>();
    public static SharedPreferences   preferences;
    public static NotificationManager manager;
    public static String              HTTP                 = "192.168.1.5";


    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        font = Typeface.createFromAsset(context.getAssets(), "font/yekan.ttf");
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        alarmManger = (AlarmManager) getSystemService(context.ALARM_SERVICE);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // preferences.edit().clear();
        File file = new File(DIR_APP);
        // Typeface font = Typeface.createFromAsset(Context.getAssets(), "fonts/DroidNaskh.ttf");
        new File(DIR_PRODUCT).mkdirs();
        test("product/Martyr.png", null, null);
        // if ( !file.exists()) {
        copyDB();
        copyFlag();
        copyPhoto();
        //deleteDirectory(file);
        /*  Thread thread = new Thread(new Runnable() {

              @Override
              public void run() {
                  new File(DIR_SOUND).mkdirs();
                  
                  copyContinent();
              }
          });
          thread.start();*/

        //  }

    }


    public static boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }


    public void copyDB() {
        try {
            new File(DIR_DB).mkdirs();
            AssetManager manager = context.getAssets();
            InputStream inputStream;
            File file = new File(DIR_DB + DB);
            if ( !file.exists()) {
                inputStream = manager.open("Db" + DB);
                HelperIO.copyFile(inputStream, DIR_DB + DB);
            }

        }
        catch (Exception e) {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir("file", Context.MODE_PRIVATE);
            directory.mkdirs();
            File mypath = new File(directory, DB);
            AssetManager manager = context.getAssets();
            InputStream inputStream = null;
            if ( !mypath.exists()) {
                try {
                    inputStream = manager.open("Db" + DB);
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }

                HelperIO.copyFile(inputStream, mypath + "Db" + DB);

            }

        }
    }


    private void copyPhoto() {

        for (i = 1; i < photoContinent.length + 1; i++) {
            String[] word = listFiles("photo/" + photoContinent[i - 1]);
            Log.i("LOG", "word" + word.length);
            for (f = 1; f < word.length + 1; f++) {
                //String h[] = null;
                // h[f] = word[f - 1];
                String[] coun = listFiles("photo/" + photoContinent[i - 1] + "/" + word[f - 1]);
                // new File(DIR_PHOTO + photoContinent[i - 1] + "/" + word[f - 1]).mkdirs();
                Log.i("LOG", "coun" + coun.length);
                for (j = 0; j < coun.length; j++) {
                    //String h2 = h[j - 1];
                    new File(DIR_PHOTO + photoContinent[i - 1] + "/" + word[f - 1]).mkdirs();
                    //String[] mgm = listFiles("photo/" + photoContinent[i - 1] + "/" + word[f - 1]);
                    test("photo/" + photoContinent[i - 1] + "/" + word[f - 1] + "/" + coun[j], DIR_PHOTO, photoContinent[i - 1] + "/" + word[f - 1]);
                    // Log.i("LOG", "mgm" + mgm.length);

                }
            }
        }
    }


    private void copyContinent() {

        Log.i("LOG", "Percent   2");
        for (i = 0; i < photoContinent.length; i++) {
            listFiles("word/" + photoContinent[i]);
            new File(DIR_CONTINENT + photoContinent[i]).mkdirs();
            for (j = 0; j < fileList.length; j++) {
                test("word/" + photoContinent[i] + "/" + fileList[j], DIR_CONTINENT, photoContinent[i]);
            }

        }
    }


    private void copyFlag() {

        for (i = 1; i < photoContinent.length + 1; i++) {
            String[] word = listFiles("flag/" + photoContinent[i - 1]);
            Log.i("LOG", "word" + word.length);
            new File(DIR_FLAG + photoContinent[i - 1]).mkdirs();
            for (j = 0; j < word.length; j++) {

                test("flag/" + photoContinent[i - 1] + "/" + word[j], DIR_FLAG, photoContinent[i - 1]);

            }
        }
    }


    private void test(String str, String des, String add) {
        /* Log.i("", str + "::::::" + des);
         Log.i("", add);
         Log.i("", "/" + fileList[j]);*/
        if (des != null && add != null) {

            AssetManager assetManager = context.getAssets();
            InputStream inputStream;
            try {
                inputStream = assetManager.open(str);
                inputStream = assetManager.open(str);
                HelperIO.copyFile(inputStream, des + add + "/" + fileList[j]);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream;
            try {
                inputStream = assetManager.open(str);
                inputStream = assetManager.open(str);
                HelperIO.copyFile(inputStream, G.DIR_PRODUCT + "/" + "Martyr.png");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
