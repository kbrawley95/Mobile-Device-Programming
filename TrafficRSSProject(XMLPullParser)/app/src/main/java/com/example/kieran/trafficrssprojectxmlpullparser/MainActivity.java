package com.example.kieran.trafficrssprojectxmlpullparser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

/**
 * Created by Kieran Brawley. Matric No:S1433740
 */

public class MainActivity extends AppCompatActivity {

    ArrayList<RSSItem> rssItemsArray;
    RSSAdapter rssAdapter;
    ListView rssItemsList;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.i("RSSFeed", "starting download Task");
        DownloadFile();

        //Get reference to our ListView
        rssItemsList = (ListView) findViewById(R.id.rssList);

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

        //Set the click listener to launch the browser when a row is clicked.
        rssItemsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {

                String coords=rssItemsArray.get(pos).getGeorssPoint();
                String uri = String.format("geo:"+ coords);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
                return true;

            }
        });

        searchView=(SearchView)findViewById(R.id.roadworksSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                final ArrayList<RSSItem> filteredResults = new ArrayList<RSSItem>();
                for (int i = 0; i < rssItemsArray.size(); i++) {
                    if (rssItemsArray.get(i).getTitle().toLowerCase().contains(query.toLowerCase())) {
                        filteredResults.add(rssItemsArray.get(i));
                    }
                }

                rssItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String url = filteredResults.get(position).getLink();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });

                //Set the click listener to launch the browser when a row is clicked.
                rssItemsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {

                        String coords = filteredResults.get(pos).getGeorssPoint();
                        String uri = String.format("geo:" + coords);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(intent);
                        return true;

                    }
                });

                rssAdapter = new RSSAdapter(MainActivity.this, filteredResults);

                rssItemsList.setAdapter(rssAdapter);
                rssItemsList.deferNotifyDataSetChanged();


                return true;
            }
        });
    }

    /*    if(!isNetworkAvailable()){
            //Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Closing the App")
                    .setMessage("No Internet Connection,check your settings")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    });
            AlertDialog alert=builder.create();
            alert.show();
        }


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }*/





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


