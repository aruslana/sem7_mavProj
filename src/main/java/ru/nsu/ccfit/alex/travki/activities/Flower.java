package ru.nsu.ccfit.alex.travki.activities;

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

import ru.nsu.ccfit.alex.travki.db.DBHelper;
import ru.nsu.ccfit.alex.travki.db.DataForDB;
import ru.nsu.ccfit.alex.travki.R;

/**
 * Created by alexandra on 26.11.17.
 */

public class Flower extends AppCompatActivity {
    private Spinner spin1, spin2;
    private Button but_back, but_next;
    private DBHelper dbHelper;
    private DataForDB dataDB = DataForDB.getInstance();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flower);

        spin1 = (Spinner)findViewById(R.id.flow_spin1);
        spin2 = (Spinner)findViewById(R.id.flow_spin2);

        but_back = (Button) findViewById(R.id.flow_butb);
        but_next = (Button) findViewById(R.id.flow_butn);

        dbHelper = new DBHelper(Flower.this);

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

        String[] f_form = dbHelper.createListElems("flow_form", new String[] {"name"});
        String[] f_gr = dbHelper.createListElems("flow_group", new String[] {"name"});

        ArrayAdapter<String> adapt1 = new ArrayAdapter<>(this, R.layout.row, R.id.row_elname, f_form);
        spin1.setAdapter(adapt1);

        ArrayAdapter<String> adapt2 = new ArrayAdapter<>(this, R.layout.row, R.id.row_elname, f_gr);
        spin2.setAdapter(adapt2);

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                if(0 != selectedItemPosition) {
                    String el = (String) parent.getItemAtPosition(selectedItemPosition);
                    dataDB.setFlowForm(el);
                }else {
                    dataDB.setFlowForm(null);
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
                    dataDB.setFlowGroup(el);
                }else {
                    dataDB.setFlowGroup(null);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Flower.this, Leaf.class);
                startActivity(intent);
                dbHelper.close();
                Flower.this.finish();
            }
        });

        but_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Flower.this, Result.class);
                startActivity(intent);
                dbHelper.close();
                Flower.this.finish();
            }
        });
    }

}
