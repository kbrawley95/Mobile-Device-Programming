package com.example.kieran.trafficrssprojectxmlpullparser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.logging.LogRecord;

/**
 * Created by Kieran Brawley. Matric No:S1433740
 */
public class RSSAdapter extends ArrayAdapter<RSSItem>{

    public RSSAdapter(Context context, ArrayList<RSSItem> results){
        super(context,0,results);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){

        //Get the data item for this position
        RSSItem rssItem=getItem(pos);
        //Check if an existing view is being reused, otherwise inflate the view
        if(convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.row_site, parent,false);

            //Lookup view for data population
            TextView titleText=(TextView)convertView.findViewById(R.id.homeText);
            TextView descriptionText=(TextView)convertView.findViewById(R.id.descriptionText);
            TextView linkText=(TextView)convertView.findViewById(R.id.linkText);
            TextView pubDateText=(TextView)convertView.findViewById(R.id.pubDateText);
            //Populate the data in the template view using the data object
            titleText.setText(rssItem.getTitle());
            descriptionText.setText(rssItem.getDescription());
            linkText.setText(rssItem.getLink());
            pubDateText.setText(rssItem.getPubDate());


        }

        return convertView;
    }

    @Override
    public android.widget.Filter getFilter() {
        return super.getFilter();
    }

    
}


