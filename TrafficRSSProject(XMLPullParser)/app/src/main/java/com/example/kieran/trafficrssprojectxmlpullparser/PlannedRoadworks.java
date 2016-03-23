package com.example.kieran.trafficrssprojectxmlpullparser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PlannedRoadworks extends AppCompatActivity {

    ArrayList<RSSItem> rssItemsArray;
    RSSAdapter rssAdapter;
    ListView rssItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planned_roadworks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.i("RSSFeed", "starting download Task");
        DownloadFile();

        //Get reference to our ListView
        rssItemsList = (ListView)findViewById(R.id.plannedRSSList);

        //Set the click listener to launch the browser when a row is clicked.
        rssItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                String url = rssItemsArray.get(pos).getLink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }

        });

    }

    private void DownloadFile() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    rssItemsArray = (ArrayList) RSSDownloader.DownloadFromURL("http://trafficscotland.org/rss/feeds/plannedroadworks.aspx");
                    Log.i("Results", rssItemsArray.size() + "");

                    if (rssItemsArray.size() > 0) {
                        String[] stuff = new String[rssItemsArray.size()];
                        for (int i = 0; i < stuff.length; i++) {
                            stuff[i] = rssItemsArray.get(i).getTitle();
                            stuff[i]=rssItemsArray.get(i).getDescription();
                            stuff[i]=rssItemsArray.get(i).getLink();
                            stuff[i]=rssItemsArray.get(i).getPubDate();
                        }
                        rssAdapter=new RSSAdapter(PlannedRoadworks.this, rssItemsArray);

                        PlannedRoadworks.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // This code will always run on the UI thread, therefore is safe to modify UI elements.
                                rssItemsList = (ListView) findViewById(R.id.plannedRSSList);
                                rssItemsList.setAdapter(rssAdapter);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Results", "ERROR" + e);
                }
            }
        });
        thread.start();
    }

}
