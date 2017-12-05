package ru.nsu.ccfit.alex.travki.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ru.nsu.ccfit.alex.travki.R;

public class Main extends AppCompatActivity {
    private Button steb, leaf, flow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        steb = (Button) findViewById(R.id.but0);
        leaf = (Button) findViewById(R.id.but1);
        flow = (Button) findViewById(R.id.but2);

        steb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, Steb.class);
                startActivity(intent);
                Main.this.finish();
            }
        });
        leaf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, Leaf.class);
                startActivity(intent);
                Main.this.finish();
            }
        });
        flow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, Flower.class);
                startActivity(intent);
                Main.this.finish();
            }
        });
    }
}
