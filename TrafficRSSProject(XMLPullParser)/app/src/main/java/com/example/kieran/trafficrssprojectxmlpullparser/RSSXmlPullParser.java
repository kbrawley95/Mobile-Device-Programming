package com.example.kieran.trafficrssprojectxmlpullparser;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kieran Brawley. Matric No:S1433740
 */
public class RSSXmlPullParser {

    private static String xmlDoc="http://trafficscotland.org/rss/feeds/roadworks.aspx";
    static final String KEY_ITEM="item";
    static final String KEY_TITLE="title";
    static final String KEY_DESCRIPTION="description";
    static final String KEY_LINK="link";
    static final String KEY_PUBLICATION_DATE="pubDate";

    public static List<RSSItem>getRSSItemsFromFile(XmlPullParser myParser) {
        List<RSSItem> result = new ArrayList<RSSItem>();
        int event;
        String text = null;
        try
        {
           event = myParser.getEventType();
            int counter = 0;
            while(event != XmlPullParser.END_DOCUMENT)
            {
                String name = myParser.getName();
                switch (event)
                {
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (name.equalsIgnoreCase(KEY_TITLE))
                        {
                            RSSItem temp = new RSSItem();
                            result.add(temp);
                            result.get(counter).setTitle(text);
                        }
                        else if (name.equalsIgnoreCase(KEY_DESCRIPTION))
                        {
                                result.get(counter).setDescription(text);
                        }
                        else if (name.equalsIgnoreCase(KEY_LINK))
                        {
                            result.get(counter).setLink(text);
                        } else if (name.equalsIgnoreCase(KEY_PUBLICATION_DATE))
                        {
                            result.get(counter).setPubDate(text);
                            counter++;
                        }
                        break;
                }

                event = myParser.next();
            }
        }
        catch (Exception e)
        {
            Log.e("Results", "Couldn't do xmlParsing " + e);
        }

        return result;
    }
}


