package com.example.kieran.trafficrssprojectxmlpullparser;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.webkit.WebView;

import org.xmlpull.v1.XmlPullParser;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kieran Brawley. Matric No:S1433740
 */

public class MainActivity extends AppCompatActivity {

    ArrayList<RSSItem> rssItemsArray;
    RSSAdapter rssAdapter;
    ListView rssItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.i("RSSFeed", "starting download Task");
        DownloadFile();

        //Get reference to our ListView
        rssItemsList = (ListView)findViewById(R.id.rssList);

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
                   rssItemsArray = (ArrayList) RSSDownloader.DownloadFromURL("http://trafficscotland.org/rss/feeds/currentincidents.aspx");
                   Log.i("Results", rssItemsArray.size() + "");

                   if (rssItemsArray.size() > 0) {
                       String[] stuff = new String[rssItemsArray.size()];
                       for (int i = 0; i < stuff.length; i++) {
                           stuff[i] = rssItemsArray.get(i).getTitle();
                           stuff[i]=rssItemsArray.get(i).getDescription();
                           stuff[i]=rssItemsArray.get(i).getLink();
                           stuff[i]=rssItemsArray.get(i).getPubDate();
                       }
                       rssAdapter=new RSSAdapter(MainActivity.this, rssItemsArray);

                       MainActivity.this.runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               // This code will always run on the UI thread, therefore is safe to modify UI elements.
                               rssItemsList = (ListView) findViewById(R.id.rssList);
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


