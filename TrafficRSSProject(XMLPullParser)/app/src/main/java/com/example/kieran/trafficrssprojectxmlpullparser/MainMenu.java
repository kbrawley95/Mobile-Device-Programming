package com.example.kieran.trafficrssprojectxmlpullparser;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class MainMenu extends AppCompatActivity {

    Button recentUpdatesButton;
    Button currentRoadworks;
    Button plannedRoadworks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recentUpdatesButton=(Button)findViewById(R.id.recentUpdatesButton);
        recentUpdatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainMenu.this, MainActivity.class);
                startActivity(i);
            }
        });

        plannedRoadworks=(Button)findViewById(R.id.plannedRoadworks);
        plannedRoadworks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainMenu.this,PlannedRoadworks.class);
                startActivity(i);
            }
        });

        currentRoadworks=(Button)findViewById(R.id.currentRoadworks);
        currentRoadworks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainMenu.this,CurrentRoadworks.class);
                startActivity(i);
            }
        });


    }

}
