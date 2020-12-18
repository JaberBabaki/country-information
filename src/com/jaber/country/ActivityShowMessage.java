package com.jaber.country;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;


public class ActivityShowMessage extends Activity {

    public int            id;
    public String         title;
    public String         date;
    public String         text;
    public SQLiteDatabase newDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_message);
        newDb = SQLiteDatabase.openDatabase(G.DIR_DB + G.DB, null, SQLiteDatabase.OPEN_READWRITE);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
            title = extras.getString("title");
            text = extras.getString("date");
            date = extras.getString("text");

        }
        TextView txtheader = (TextView) findViewById(R.id.txtheader);
        TextView txtDateMain = (TextView) findViewById(R.id.txtDateMain);
        TextView txtMainMain = (TextView) findViewById(R.id.txtMainMain);
        txtheader.setText(title);
        txtDateMain.setText(text);
        txtMainMain.setText(date);
        txtMainMain.setLineSpacing(1, 2);
        newDb.execSQL("UPDATE Message SET flag  = 1  where  id =" + id);
        newDb.close();
        //Message.adapter.notifyDataSetChanged();
    }

}
