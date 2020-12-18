package com.jaber.country;

import java.util.ArrayList;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Message extends Activity {

    public ArrayList<StructMessage> Messages = new ArrayList<StructMessage>();
    public ArrayAdapter             adapter;
    public SQLiteDatabase           newDb;


    @Override
    protected void onResume() {

        G.currentActivity = this;
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            Log.i("LOOOG", "notifyDataSet");
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        ListView lstContent = (ListView) findViewById(R.id.lstMessage);
        newDb = SQLiteDatabase.openDatabase(G.DIR_DB + G.DB, null, SQLiteDatabase.OPEN_READWRITE);
        adapter = new AdapterMessage(Messages);
        lstContent.setAdapter(adapter);
        Cursor cursor112 = newDb.rawQuery("SELECT * FROM Message", null);
        Messages.clear();
        while (cursor112.moveToNext()) {
            StructMessage message = new StructMessage();
            message.id = cursor112.getInt(cursor112.getColumnIndex("id"));
            message.title = cursor112.getString(cursor112.getColumnIndex("title"));
            message.text = cursor112.getString(cursor112.getColumnIndex("text"));
            message.date = cursor112.getString(cursor112.getColumnIndex("date"));
            message.readOnRead = cursor112.getInt(cursor112.getColumnIndex("flag"));
            Messages.add(message);
        }
        cursor112.close();
        newDb.close();
        adapter.notifyDataSetChanged();
    }

}
