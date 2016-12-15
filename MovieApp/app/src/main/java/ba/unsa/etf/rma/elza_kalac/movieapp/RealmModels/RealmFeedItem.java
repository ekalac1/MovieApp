package ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels;



import io.realm.RealmObject;

public class RealmFeedItem extends RealmObject {

    private String mtitle;
    private String mlink;
    private String Author;
    private String mpubDate;
    private String mGuid;

    public RealmFeedItem() {}
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
