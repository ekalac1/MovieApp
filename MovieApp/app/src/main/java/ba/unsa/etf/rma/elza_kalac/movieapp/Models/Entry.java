package ba.unsa.etf.rma.elza_kalac.movieapp.Models;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Laptop on 07.10.2016..
 */
public class Entry {

    private String title;
    private String link;
    private String description;
    private String author;
    private String pubDate;

    public Entry(String title, String link, String description, String author, String pubDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.author = author;
        this.pubDate = pubDate;
    }

    public Entry() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
