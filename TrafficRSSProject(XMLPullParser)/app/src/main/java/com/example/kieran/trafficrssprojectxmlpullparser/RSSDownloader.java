package com.example.kieran.trafficrssprojectxmlpullparser;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Kieran Brawley. Matric No:S1433740
 */
public class RSSDownloader {

    //Tag for Log statements
    private static String myTag="RSSItems";

    //Handler msg that represents we are posting a progress update.
    static final int POST_PROGRESS=1;

    /************************************************
     * Download a file from the Internet and store it locally
     *
     * @param URL - the url of the file to download
     ************************************************/

    public static List<RSSItem> DownloadFromURL(String URL){//this is the downloader method
        try{
            Log.d(myTag, "download beginning");
            URL url=new URL(URL);   ///URL of the file

            //keep the start time so we can display how long it took to the Log.
            long startTime=System.currentTimeMillis();
            Log.d(myTag, "download beginning");

            //Open a connection to that URL
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);

            //this will be useful so that you can show a typical 0-100% progress bar
            //int lengthOffile=ucon.getContentLength();
            Log.i(myTag, "Opened Connection");

            /************************************************
             * Define InputStreams to read from the URLConnection.
             ************************************************/
            //Start the query
            urlConnection.connect();
            InputStream stream = urlConnection.getInputStream();

            XmlPullParserFactory xmlPullParser = XmlPullParserFactory.newInstance();
            XmlPullParser myparser = xmlPullParser.newPullParser();

            myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myparser.setInput(stream, null);

            List<RSSItem> result = RSSXmlPullParser.getRSSItemsFromFile(myparser);
            stream.close();
            return result;
        }catch(Exception e){
            Log.d(myTag, "Error: "+ e);
        }

        return null;
    }

}
