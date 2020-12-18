package com.jaber.country;

import java.util.ArrayList;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Product extends Activity {

    public ArrayList<StructProduct> Products = new ArrayList<StructProduct>();
    public ArrayAdapter             adapter;
    public SQLiteDatabase           newDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product);
        ListView lstContent = (ListView) findViewById(R.id.lstMessage);
        newDb = SQLiteDatabase.openDatabase(G.DIR_DB + G.DB, null, SQLiteDatabase.OPEN_READWRITE);
        adapter = new AdapterProduct(Products);
        lstContent.setAdapter(adapter);
        Cursor cursor112 = newDb.rawQuery("SELECT * FROM Product", null);
        Products.clear();
        while (cursor112.moveToNext()) {
            StructProduct product = new StructProduct();
            product.id = cursor112.getInt(cursor112.getColumnIndex("id"));
            product.title = cursor112.getString(cursor112.getColumnIndex("title"));
            product.text = cursor112.getString(cursor112.getColumnIndex("text"));
            product.pack = cursor112.getString(cursor112.getColumnIndex("pack"));
            product.icon = cursor112.getString(cursor112.getColumnIndex("icon"));
            Products.add(product);
        }
        cursor112.close();
        newDb.close();
        adapter.notifyDataSetChanged();
    }
}
