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

    CustomFilter filter;
    ArrayList<RSSItem> filterList;
    ArrayList<RSSItem>gainedResults;

 /*   public RSSAdapter(Context context, ArrayList<RSSItem> results){
        super(context,0,results);
    }*/

    public RSSAdapter(Context context, ArrayList<RSSItem> results){
        super(context,0,results);
        this.filterList=results;
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
    public Filter getFilter() {
        if(filter==null){
            filter=new CustomFilter();
        }

        return filter;
    }

    class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results=new FilterResults();

            if(constraint!=null && constraint.length()>0){
                //Constrant to upper
                constraint=constraint.toString().toUpperCase();

                ArrayList<RSSItem> filters=new ArrayList<RSSItem>();

                //get specific items
                for(int i=0; i<filterList.size(); i++) {
                    if (filterList.get(i).getTitle().toUpperCase().contains(constraint)) {
                        RSSItem rssItem=new RSSItem(filterList.get(i).getTitle(),filterList.get(i).getDescription(), filterList.get(i).getLink(), filterList.get(i).getPubDate());

                        filters.add(rssItem);

                    }
                }
                results.count=filters.size();
                results.values=filters;
            }else {
                results.count=filterList.size();
                results.values=filterList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            gainedResults=(ArrayList<RSSItem>)results.values;
            notifyDataSetChanged();
        }


    }
}


