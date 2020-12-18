package com.jaber.country;

import android.app.Activity;
import android.widget.TextView;


public class ActivityEnhanced extends Activity {

    /*public static final String DIR_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String DIR_DB     = DIR_SDCARD + "/DB1-country/";
    public static String       DI_DB      = "/DB-country.sqlite";
    public static String       DIR_APP    = DIR_SDCARD + DIR_DB;*/
    public static int      sumEuropa;
    public static TextView txtSumEuropa;


    @Override
    protected void onResume() {
        G.currentActivity = this;
        G.currentActivity2 = getClass().getSimpleName();

        //  sumEuropa = 0;
        //G.txtSumEuropa = null;
        super.onResume();
    }

    /* @Override
     public void onBackPressed() {

         Toast toast = Toast.makeText(this, "برای خروج یکبار دیگر ضربه بزنید",
                 Toast.LENGTH_SHORT);
         if (backPressTime >= System.currentTimeMillis() - 2000L) {



             if (toast != null)
                 toast.cancel();
             super.onBackPressed();


             toast.show();
             backPressTime = System.currentTimeMillis();
         }
     }*/
}
