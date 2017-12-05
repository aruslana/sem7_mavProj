package ru.nsu.ccfit.alex.travki;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.IOException;

/**
 * Created by alexandra on 11.10.17.
 */

public class Steb extends AppCompatActivity{
    private Spinner spin1, spin2, spin3;
    private Button but_back, but_next;
    private DBHelper dbHelper;
    private DataForDB dataDB = DataForDB.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steb);

        spin1 = (Spinner)findViewById(R.id.steb_spin1);
        spin2 = (Spinner)findViewById(R.id.steb_spin2);
        spin3 = (Spinner)findViewById(R.id.steb_spin3);

        but_back = (Button) findViewById(R.id.leaf_butb);
        but_next = (Button) findViewById(R.id.leaf_butn);

        dbHelper = new DBHelper(Steb.this);

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

        SpinnerAdapter adapt1 = new SpinnerAdapter(this, R.layout.row_with_img,
                dbHelper.createHMElems("leaf_type", new String[] {"name"}, new Integer[] {R.drawable.leaf_prost, R.drawable.suprot}));
        spin1.setAdapter(adapt1);

        SpinnerAdapter adapt2 = new SpinnerAdapter(this, R.layout.row_with_img,
                dbHelper.createHMElems("leaf_set", new String[] {"name"}, new Integer[] {R.drawable.leaf_cher, R.drawable.leaf_sid}));
        spin2.setAdapter(adapt2);

        SpinnerAdapter adapt3 = new SpinnerAdapter(this, R.layout.row_with_img,
                dbHelper.createHMElems("leaf_form", new String[] {"name"}, new Integer[] {R.drawable.ochered, R.drawable.suprot, R.drawable.mutov}));
        spin3.setAdapter(adapt3);

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                if(0 != selectedItemPosition) {
                    String el = (String) parent.getItemAtPosition(selectedItemPosition);
                    dataDB.setStemStruct(el);
                }else {
                    dataDB.setStemStruct(null);
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
                    dataDB.setStemType(el);
                }else {
                    dataDB.setStemType(null);
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
                    dataDB.setStemLeaf(el);
                }else {
                    dataDB.setStemLeaf(null);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Steb.this, Main.class);
                startActivity(intent);
                dbHelper.close();
                Steb.this.finish();
            }
        });

        but_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Steb.this, Leaf.class);
                startActivity(intent);
                dbHelper.close();
                Steb.this.finish();
            }
        });

    }
}
