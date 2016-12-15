package ba.unsa.etf.rma.elza_kalac.movieapp.Models;



import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.RealmFeedItem;

@Root(name = "item", strict = false)
public class FeedItem implements Serializable {

    @Element(name = "title")
    private String mtitle;
    @Element(name = "link")
    private String mlink;
    @Element(name = "author")
    private String Author;
    @Element(name = "pubDate")
    private String mpubDate;
    @Element(name = "guid")
    private String mGuid;

    public FeedItem() {}


    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public String getMlink() {
        return mlink;
    }

    public void setMlink(String mlink) {
        this.mlink = mlink;
    }


    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getMpubDate() {
        return mpubDate;
    }

    public void setMpubDate(String mpubDate) {
        this.mpubDate = mpubDate;
    }

    public String getmGuid() {
        return mGuid;
    }

    public void setmGuid(String mGuid) {
        this.mGuid = mGuid;
    }

    public RealmFeedItem getRealmFeedItem()
    {
        RealmFeedItem item= new RealmFeedItem();
        item.setAuthor(this.Author);
        item.setmGuid(this.mGuid);
        item.setMlink(this.mlink);
        item.setMpubDate(this.mpubDate);
        item.setMtitle(this.mtitle);
        return item;
    }

    public FeedItem getFeedItem(RealmFeedItem realmFeedItem)
    {
        FeedItem item = new FeedItem();
        item.setAuthor(realmFeedItem.getAuthor());
        item.setmGuid(realmFeedItem.getmGuid());
        item.setMlink(realmFeedItem.getMlink());
        item.setMpubDate(realmFeedItem.getMpubDate());
        item.setMtitle(realmFeedItem.getMtitle());
        return item;

    }
}
