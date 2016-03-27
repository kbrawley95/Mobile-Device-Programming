package com.example.kieran.trafficrssprojectxmlpullparser;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.Filter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.logging.LogRecord;

/**
 * Created by Kieran Brawley. Matric No:S1433740
 */
public class RSSAdapter extends ArrayAdapter<RSSItem>{


 /*   public RSSAdapter(Context context, ArrayList<RSSItem> results){
        super(context,0,results);
    }*/

    public RSSAdapter(Context context, ArrayList<RSSItem> results){
        super(context,0,results);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){

        RelativeLayout row=(RelativeLayout)convertView;

        //Check if an existing view is being reused, otherwise inflate the view
        if(row==null) {
            LayoutInflater inflater=(LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=(RelativeLayout)inflater.inflate(R.layout.row_site, null);
        }

            //Lookup view for data population
            TextView titleText=(TextView)row.findViewById(R.id.homeText);
            TextView descriptionText=(TextView)row.findViewById(R.id.descriptionText);
            TextView linkText=(TextView)row.findViewById(R.id.linkText);
            TextView pubDateText=(TextView)row.findViewById(R.id.pubDateText);

            //Populate the data in the template view using the data object
            titleText.setText(getItem(pos).getTitle());
            descriptionText.setText(getItem(pos).getDescription());
            linkText.setText(getItem(pos).getLink());
            pubDateText.setText(getItem(pos).getPubDate());

        return row;
    }
}


