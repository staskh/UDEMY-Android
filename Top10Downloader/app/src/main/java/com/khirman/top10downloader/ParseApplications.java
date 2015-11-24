package com.khirman.top10downloader;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by stas on 10/19/15.
 */
public class ParseApplications {
    private String xmlData;
    private ArrayList<Application> applications;

    public ParseApplications(String xmlData) {
        this.xmlData = xmlData;
        applications = new ArrayList<>();
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }

    public void process(){
        try {
            boolean inEntry=false;
            Application app=null;
            String tagName=null;
            String tagValue=null;

            XmlPullParserFactory xmlFactory = XmlPullParserFactory.newInstance();
            xmlFactory.setNamespaceAware(true);
            XmlPullParser xpp = xmlFactory.newPullParser();

            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();
            while(eventType!=XmlPullParser.END_DOCUMENT){
                tagName = xpp.getName();
                if(eventType == XmlPullParser.START_TAG) {
                    //Log.d("ParseApplication","Start Tag: "+tagName);
                    if(tagName.equalsIgnoreCase("entry")){
                        inEntry = true;
                        app= new Application();
                        //Log.d("ParseApplication","Start ENTRY -------");
                    }
                } else if(eventType == XmlPullParser.END_TAG) {
                    //Log.d("ParseApplication","End   Tag: "+tagName);
                    if(tagName.equalsIgnoreCase("entry")){
                        applications.add(app);
                        inEntry = false;
                        //Log.d("ParseApplication", "End   ENTRY -------");
                    } else if(inEntry){
                        if(tagName.equalsIgnoreCase("name")){
                            app.setName(tagValue);
                        } else if(tagName.equalsIgnoreCase("artist")){
                            app.setArtist(tagValue);
                        } else if(tagName.equalsIgnoreCase("releasedate")){
                            app.setReleaseDate(tagValue);
                        }
                    }

                } else if (eventType == XmlPullParser.TEXT ) {
                    tagValue = xpp.getText();
                }
                eventType = xpp.next();
            }

            for(Application it: applications){
                Log.d("ParseApplication","-----------------");
                Log.d("ParseApplication","Name   : " +it.getName());
                Log.d("ParseApplication","Artist : " +it.getArtist());
                Log.d("ParseApplication","Release: " +it.getReleaseDate());
            }

        } catch (XmlPullParserException | IOException e) {
            Log.d("ParseApplication", "Exception: " + e.toString());
            e.printStackTrace();
        }

    }

}
