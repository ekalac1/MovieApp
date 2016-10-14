package ba.unsa.etf.rma.elza_kalac.movieapp.Models;



import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class FeedItem implements Serializable {

    @Element(name = "title")
    private String mtitle;
    @Element(name = "link")
    private String mlink;
  /*  @Element(name = "description")
    private String mdescription; */
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
}
