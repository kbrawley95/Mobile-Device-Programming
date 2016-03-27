package com.example.kieran.trafficrssprojectxmlpullparser;

import android.content.Context;
import android.text.Html;
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


    static final String KEY_TITLE="title";
    static final String KEY_DESCRIPTION="description";
    static final String KEY_LINK="link";
    static final String KEY_PUBLICATION_DATE="pubDate";
    static final String KEY_COORDINATES="georss:point";

    public static List<RSSItem>getRSSItemsFromFile(XmlPullParser myParser) {
        List<RSSItem> result = new ArrayList<RSSItem>();
        int event;
        String text = null;
        String textChar;
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
                        if (name.equals(KEY_TITLE))
                        {
                            RSSItem temp = new RSSItem("", "","","", "");
                            result.add(temp);
                            result.get(counter).setTitle(text);
                        }
                        else if (name.equals(KEY_DESCRIPTION)) {
                                result.get(counter).setDescription(Html.fromHtml(text).toString());
                        }
                        else if (name.equals(KEY_LINK))
                        {
                            result.get(counter).setLink(text);
                        }else if (name.equals(KEY_COORDINATES))
                        {
                            result.get(counter).setGeorssPoint(text);
                        }
                        else if (name.equals(KEY_PUBLICATION_DATE))
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


