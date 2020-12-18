package com.jaber.country;

import java.util.ArrayList;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class AdapterCountry extends ArrayAdapter<StructCountry> {

    public static Activity a;
    public static String   nameTable;

    public static String   setRang;


    public AdapterCountry(ArrayList<StructCountry> array) {
        super(G.context, R.layout.adapter_country, array);

    }


    private static class ViewHolder {

        public CustomTextView txtName;
        public TextView       txtLikeLocal;
        public ImageView      imgLike;
        public Bitmap         bitmap;
        public ImageView      imgFlag;
        public LinearLayout   layAdapter;
        public SQLiteDatabase newDb;
        InternetRecive        internetRecive;


        public ViewHolder(View view) {
            txtName = (CustomTextView) view.findViewById(R.id.txtName);
            txtLikeLocal = (TextView) view.findViewById(R.id.txtLikeLocal);
            imgLike = (ImageView) view.findViewById(R.id.imgFavoriteLocal);
            imgFlag = (ImageView) view.findViewById(R.id.imgFlag);
            layAdapter = (LinearLayout) view.findViewById(R.id.layAdapter);
            internetRecive = new InternetRecive();
        }


        public void fill(ArrayAdapter<StructCountry> adapter, final StructCountry item, int position) {
            txtName.setText(item.name);
            txtLikeLocal.setText("" + item.likeLocal);
            txtName.setTextColor(Color.parseColor(setRang));
            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inSampleSize = 3;
            bitmap = BitmapFactory.decodeFile(G.DIR_FLAG + nameTable + "/" + item.id + ".png", op);
            Log.i("LOG", "" + G.DIR_FLAG + nameTable + "/" + item.id + ".png");
            imgFlag.setImageBitmap(bitmap);
            // txtLikeLocal.setTypeface(G.font);
            imgFlag.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    /*  Intent intent = new Intent(G.currentActivity, Image.class);
                      intent.putExtra("id", G.DIR_FLAG + nameTable + "/" + item.id + ".png");
                      G.currentActivity.startActivity(intent);*/

                    final Dialog dialog2 = new Dialog(G.currentActivity);
                    dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog2.setContentView(R.layout.dialog_lay);
                    ImageView imgDialogMain = (ImageView) dialog2.findViewById(R.id.imgDialogMain);
                    imgDialogMain.setImageBitmap(bitmap);
                    dialog2.setCancelable(true);
                    dialog2.setCanceledOnTouchOutside(true);
                    dialog2.show();
                }
            });
            if (item.imgLike == 0) {
                imgLike.setImageResource(R.drawable.bic);

            } else {
                imgLike.setImageResource(R.drawable.favo);
            }

            imgLike.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    newDb = SQLiteDatabase.openDatabase(G.DIR_DB + G.DB, null, SQLiteDatabase.OPEN_READWRITE);
                    Cursor cursor = newDb.rawQuery("SELECT * FROM '" + nameTable + "' where id =" + item.id, null);
                    cursor.moveToNext();
                    int favar = cursor.getInt(cursor.getColumnIndex("Like_local"));
                    int fava = cursor.getInt(cursor.getColumnIndex("LIKE"));
                    cursor.close();
                    if (favar == 0) {
                        imgLike.setImageResource(R.drawable.favo);
                        item.imgLike = 1;
                        newDb.execSQL("UPDATE '" + nameTable + "' SET like_Local = 1 ,LIKE='" + (fava + 1) + "',state=1  where  id =" + item.id);
                        G.indexList.add("" + item.id);
                        G.tableList.add(nameTable);
                        ActivityEurope.saveArray(G.tableList, "tableList");
                        ActivityEurope.saveArray(G.indexList, "indexList");
                        SharedPreferences.Editor editor = G.preferences.edit();
                        editor.putBoolean("CHECK", true);
                        editor.commit();
                        ActivityEnhanced.sumEuropa = ActivityEnhanced.sumEuropa + 1;
                        item.likeLocal = item.likeLocal + 1;

                        internetRecive.runer();
                    } else {
                        imgLike.setImageResource(R.drawable.bic);
                        item.imgLike = 0;
                        newDb.execSQL("UPDATE '" + nameTable + "' SET like_Local  = 0 ,LIKE='" + (fava - 1) + "',state=1 where  id =" + item.id);
                        G.indexList.add("" + item.id);
                        G.tableList.add(nameTable);
                        ActivityEurope.saveArray(G.tableList, "tableList");
                        ActivityEurope.saveArray(G.indexList, "indexList");
                        SharedPreferences.Editor editor = G.preferences.edit();
                        editor.putBoolean("CHECK", true);
                        editor.commit();
                        ActivityEnhanced.sumEuropa = ActivityEnhanced.sumEuropa - 1;
                        item.likeLocal = item.likeLocal - 1;
                        internetRecive.runer();
                    }
                    newDb.close();
                    ActivityEnhanced.txtSumEuropa.setText("" + ActivityEnhanced.sumEuropa);
                    txtLikeLocal.setText("" + item.likeLocal);
                }
            });
            layAdapter.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(G.currentActivity, ActivityShow.class);
                    intent.putExtra("id", item.id);
                    intent.putExtra("table", nameTable);
                    intent.putExtra("like", ActivityEnhanced.sumEuropa);
                    ActivityEurope.cancel();

                    G.currentActivity.startActivity(intent);
                }
            });
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        StructCountry item = getItem(position);
        if (convertView == null) {
            convertView = G.inflater.inflate(R.layout.adapter_country, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.fill(this, item, position);
        Animation animation = AnimationUtils.loadAnimation(G.currentActivity,
                android.R.anim.slide_in_left);
        convertView.startAnimation(animation);
        return convertView;
    }
}
