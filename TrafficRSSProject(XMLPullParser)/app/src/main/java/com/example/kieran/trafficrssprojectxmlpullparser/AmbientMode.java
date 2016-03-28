package com.example.kieran.trafficrssprojectxmlpullparser;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class AmbientMode extends AppCompatActivity {

    ArrayList<RSSItem> rssItemsArray;
    ArrayList<RSSItem> rssItemsArray1;
    ArrayList<RSSItem> rssItemsArray2;
    ArrayList<RSSItem> totalResults;
    RSSAdapter rssAdapter;
    ListView rssItemsList;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambient_mode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.i("RSSFeed", "starting download Task");
        DownloadFile();

        //Get reference to our ListView
        rssItemsList = (ListView)findViewById(R.id.ambientList);



        //Set the click listener to launch the browser when a row is clicked.
        rssItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                String url = totalResults.get(pos).getLink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }

        });

        //Set the click listener to launch the browser when a row is clicked.
        rssItemsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {

            /*    String coords = totalResults.get(pos).getGeorssPoint();
                String uri = String.format("geo:" + coords);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);*/
                String description=totalResults.get(pos).getDescription();

                AlertDialog .Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setMessage(description);
                builder.show();

                return true;

            }
        });

        searchView=(SearchView)findViewById(R.id.ambientSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                final ArrayList<RSSItem> filteredResults = new ArrayList<RSSItem>();
                for (int i = 0; i < totalResults.size(); i++) {
                    if (totalResults.get(i).getTitle().toLowerCase().contains(query.toLowerCase())) {
                        filteredResults.add(totalResults.get(i));

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
/*
                        String coords = filteredResults.get(pos).getGeorssPoint();
                        String uri = String.format("geo:" + coords);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(intent);
                        return true;*/

                        String description=filteredResults.get(pos).getDescription();

                        AlertDialog .Builder builder=new AlertDialog.Builder(view.getContext());
                        builder.setMessage(description);
                        builder.show();

                        return true;

                    }
                });

                rssAdapter = new RSSAdapter(AmbientMode.this, filteredResults);

                rssItemsList.setAdapter(rssAdapter);
                rssItemsList.deferNotifyDataSetChanged();


                return true;
            }
        });
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    private void DownloadFile() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    totalResults=new ArrayList<>();

                    rssItemsArray= (ArrayList) RSSDownloader.DownloadFromURL("http://trafficscotland.org/rss/feeds/plannedroadworks.aspx");
                    Log.i("Results", rssItemsArray.size() + " Planned");
                    rssItemsArray1 = (ArrayList) RSSDownloader.DownloadFromURL("http://trafficscotland.org/rss/feeds/currentincidents.aspx");
                    Log.i("Results", rssItemsArray1.size() + "Current Incidents");
                    rssItemsArray2 = (ArrayList) RSSDownloader.DownloadFromURL("http://trafficscotland.org/rss/feeds/roadworks.aspx");
                    Log.i("Results", rssItemsArray2.size() + "Roadworks");

                    totalResults.addAll(rssItemsArray);
                    totalResults.addAll(rssItemsArray1);
                    totalResults.addAll(rssItemsArray2);

                    Log.i("Results", totalResults.size() + "Total");


                    if (totalResults.size() > 0) {
                        String[] stuff = new String[totalResults.size()];
                        for (int i = 0; i < stuff.length; i++) {
                            stuff[i] = totalResults.get(i).getTitle();
                            stuff[i]=totalResults.get(i).getDescription();
                            stuff[i]=totalResults.get(i).getLink();
                            stuff[i]=totalResults.get(i).getPubDate();
                        }

                        Log.i("MyApp", "Download Shit");
                        rssAdapter=new RSSAdapter(AmbientMode.this, totalResults);

                        AmbientMode.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // This code will always run on the UI thread, therefore is safe to modify UI elements.
                                rssItemsList = (ListView) findViewById(R.id.ambientList);

                                Log.i("Results", Integer.toString(rssItemsList.getChildCount()));

                                rssItemsList.getContext().setTheme(android.R.style.Theme_Material_Wallpaper);
                                rssAdapter.setRowColor(rssItemsList.getRootView(), 0);
                                rssItemsList.setAdapter(rssAdapter);
                                rssItemsList.deferNotifyDataSetChanged();
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
