package ru.nsu.ccfit.alex.travki.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import ru.nsu.ccfit.alex.travki.db.DBHelper;
import ru.nsu.ccfit.alex.travki.db.DataForDB;
import ru.nsu.ccfit.alex.travki.R;
import ru.nsu.ccfit.alex.travki.db.ElemForDB;

/**
 * Created by alexandra on 28.11.17.
 */

public class Result extends Activity {
    private Button but_back, but_next;
    private DBHelper dbHelper;
    private DataForDB dataDB = DataForDB.getInstance();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        TableLayout tab = (TableLayout) findViewById(R.id.res_table);
        but_back = (Button) findViewById(R.id.res_butb);
        but_next = (Button) findViewById(R.id.res_butn);

        dbHelper = new DBHelper(Result.this);

        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            throw new Error("Unable to create database");
        }

        try {
            dbHelper.openDataBase();
        } catch (SQLException e) {
            throw e;
        }

        Cursor c = getCursor();
        c.moveToFirst();
        while(! c.isAfterLast()){
            TableRow row = createRow(c);
            tab.addView(row, 0);
            c.moveToNext();
        }

        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Result.this, Flower.class);
                startActivity(intent);
                dbHelper.close();
                Result.this.finish();
            }
        });

        but_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Result.this, Main.class);
                startActivity(intent);
                dbHelper.close();
                Result.this.finish();
            }
        });
    }

    private Cursor getCursor(){
        String where = null;
        ArrayList<String> args = new ArrayList<>();
        Cursor c;

        for(ElemForDB el: dataDB.getArray()){
            if(null == el.getFieldDate()) continue;

            int id = dbHelper.getID(el.getTabName(), el.getFieldDate());
            if(null == where)
                where = el.getFieldName() + " = ?";
            else
                where += " and "+el.getFieldName()+" = ?";
            args.add(String.valueOf(id));
        }
        String[] arr_arg = args.toArray(new String[0]);
        if(null == where){
            arr_arg = null;
        }

        c = dbHelper.mquery("plant", new String[] {"name", "descrip", "img"}, where,
                    arr_arg, null, null, null);

        return c;
    }

    private TableRow createRow(Cursor c){

        TableRow tr_head = new TableRow(this);
        //tr_head.setId(10);
        tr_head.setBackgroundColor(Color.GRAY);
        tr_head.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        ImageView im_viw = new ImageView(this);
        im_viw.setPadding(5, 5, 5, 5);
        byte[] bb = c.getBlob(2);
        im_viw.setImageBitmap(BitmapFactory.decodeByteArray(bb, 0, bb.length));
        tr_head.addView(im_viw);

        TextView name_plot = new TextView(this);
        name_plot.setText(c.getString(0));
        name_plot.setTextColor(Color.BLACK);
        name_plot.setTextSize(20);
        //name_plot.
        name_plot.setPadding(5, 5, 5, 5);
        tr_head.addView(name_plot);

        TextView descr_plot = new TextView(this);
        descr_plot.setText(c.getString(1));
        descr_plot.setTextColor(Color.BLACK);
        descr_plot.setPadding(5, 5, 5, 5);
        tr_head.addView(descr_plot);

        return tr_head;
    }
}
