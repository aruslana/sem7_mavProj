package ru.nsu.ccfit.alex.travki.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.nsu.ccfit.alex.travki.db.DBHelper;
import ru.nsu.ccfit.alex.travki.db.DataForDB;
import ru.nsu.ccfit.alex.travki.R;
import ru.nsu.ccfit.alex.travki.tools.SpinnerAdapter;

/**
 * Created by alexandra on 12.11.17.
 */

public class Leaf extends AppCompatActivity {
    private Spinner spin1, spin2, spin3;
    private Button but_back, but_next;
    private DBHelper dbHelper;
    private DataForDB dataDB = DataForDB.getInstance();

    private ArrayAdapter<String> adapt3;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaf);

        spin1 = (Spinner)findViewById(R.id.leaf_spin1);
        spin2 = (Spinner)findViewById(R.id.leaf_spin2);
        spin3 = (Spinner)findViewById(R.id.leaf_spin3);

        but_back = (Button) findViewById(R.id.leaf_butb);
        but_next = (Button) findViewById(R.id.leaf_butn);

        dbHelper = new DBHelper(Leaf.this);

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

        String[] l_type = dbHelper.createListElems("leaf_type", new String[] {"name"});
        final String[] full_form, form_one, form_two;
        final List<String> full_lst, one_lst, two_lst, cur_lst;

        full_form = dbHelper.createListElems("leaf_form", new String[] {"name"});
        full_lst = new ArrayList<>(Arrays.asList(full_form));
        cur_lst = new ArrayList<>(Arrays.asList(full_form));

        form_one = getFilterListElems(dbHelper.getID("leaf_type", l_type[1]));
        one_lst = new ArrayList<>(Arrays.asList(form_one));

        form_two = getFilterListElems(dbHelper.getID("leaf_type", l_type[2]));
        two_lst = new ArrayList<>(Arrays.asList(form_two));

        SpinnerAdapter adapt1 = new SpinnerAdapter(this, R.layout.row_with_img,
                dbHelper.createHMElems("leaf_type", new String[] {"name"}, new Integer[] {R.drawable.leaf_prost, R.drawable.leaf_slozh}));
        spin1.setAdapter(adapt1);

        SpinnerAdapter adapt2 = new SpinnerAdapter(this, R.layout.row_with_img,
                dbHelper.createHMElems("leaf_set", new String[] {"name"}, new Integer[] {R.drawable.leaf_cher, R.drawable.leaf_sid}));
        spin2.setAdapter(adapt2);

        adapt3 = new ArrayAdapter<>(this, R.layout.row, R.id.row_elname, cur_lst);
        spin3.setAdapter(adapt3);

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                if(0 != selectedItemPosition) {
                    String el = (String) parent.getItemAtPosition(selectedItemPosition);
                    dataDB.setLeafType(el);
                    if(1 == selectedItemPosition) {
                        updateAdapt(adapt3, one_lst);
                    }
                    if(2 == selectedItemPosition){
                        updateAdapt(adapt3, two_lst);
                    }
                }else {
                    dataDB.setLeafType(null);
                   updateAdapt(adapt3, full_lst);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                if(0 != selectedItemPosition) {
                    String el = (String) parent.getItemAtPosition(selectedItemPosition);
                    dataDB.setLeafSet(el);
                }else {
                    dataDB.setLeafSet(null);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spin3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                if(0 != selectedItemPosition) {
                    String el = (String) parent.getItemAtPosition(selectedItemPosition);
                    dataDB.setLeafForm(el);
                }else {
                    dataDB.setLeafForm(null);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Leaf.this, Steb.class);
                startActivity(intent);
                dbHelper.close();
                Leaf.this.finish();
            }
        });
        but_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Leaf.this, Flower.class);
                startActivity(intent);
                dbHelper.close();
                Leaf.this.finish();
            }
        });
    }

    private String[] getFilterListElems(int _id){
        List<String> lst = new ArrayList<>();
        lst.add("не известно");

        //"words", new String[] {"_id", "name"}, "_id = ?", new String[] {Integer.toString(num)}, null, null, null);
        Cursor c = dbHelper.mquery("leaf_form", new String[] {"name"}, "l_type = ?", new String[] {Integer.toString(_id)}, null, null, null);

        c.moveToFirst();
        while(! c.isAfterLast()){
            lst.add(c.getString(0));
            c.moveToNext();
        }
        return lst.toArray(new String[0]);
    }
    private void updateAdapt(ArrayAdapter<String> adp, List<String> newData){
        adp.clear();
        adp.addAll(newData);
        adp.notifyDataSetChanged();
    }
}
