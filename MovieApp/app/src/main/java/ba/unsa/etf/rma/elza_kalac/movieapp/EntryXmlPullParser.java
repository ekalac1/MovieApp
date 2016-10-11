package ba.unsa.etf.rma.elza_kalac.movieapp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.util.Log;

import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Entry;

public class EntryXmlPullParser {
    static final String KEY_TITLE = "title";
    static final String KEY_LINK = "link";
    static final String KEY_AUTHOR = "author";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_PUB_DATE = "pubDate";

    public static List<Entry> getStackSitesFromFile(Context ctx) {

        // List of StackSites that we will return
        List<Entry> stackSites = new ArrayList<Entry>();

        // temp holder for current StackSite while parsing
        Entry curStackSite = null;
        // temp holder for current text value while parsing
        String curText = "";

        try {
            // Get our factory and PullParser
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            // Open up InputStream and Reader of our file.
            FileInputStream fis = ctx.openFileInput("StackSites.xml");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder total = new StringBuilder();
            String line;
         /*   while ((line = reader.readLine()) != null) {
                total.append(line).append('\n');
                Log.d("xml: ", line);
            } */

            // point the parser to our file.
            xpp.setInput(reader);

            // get initial eventType
            int eventType = xpp.getEventType();

            // Loop through pull events until we reach END_DOCUMENT
            while (eventType != XmlPullParser.END_DOCUMENT) {
                // Get the current tag
                String tagname = xpp.getName();
                // Log.d("tagname: ", tagname);

                // React to different event types appropriately
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equals("item")) {
                            curStackSite = new Entry();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        //grab the current text so we can use it in END_TAG event
                        curText = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("item")) {
                            // if </site> then we are done with current Site
                            // add it to the list.
                            stackSites.add(curStackSite);
                        } else if (tagname.equalsIgnoreCase(KEY_TITLE)) {
                            // if </name> use setName() on curSite
                            curStackSite.setTitle(curText);
                        } else if (tagname.equalsIgnoreCase(KEY_DESCRIPTION)) {
                            // if </name> use setName() on curSite
                            curStackSite.setDescription(curText);
                        } else if (tagname.equalsIgnoreCase(KEY_LINK)) {
                            // if </link> use setLink() on curSite
                            curStackSite.setLink(curText);
                        } else if (tagname.equalsIgnoreCase(KEY_AUTHOR)) {
                            // if </about> use setAbout() on curSite
                            curStackSite.setAuthor(curText);
                        } else if (tagname.equalsIgnoreCase(KEY_PUB_DATE)) {
                            // if </image> use setImgUrl() on curSite
                            curStackSite.setPubDate(curText);
                        }
                        break;

                    default:
                        break;
                }
                //move on to next iteration
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // return the populated list.
        return stackSites;
    }
}
