package ba.unsa.etf.rma.elza_kalac.movieapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.RealmReview;

public class Review {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("url")
    @Expose
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RealmReview getRealmReview()
    {
        RealmReview review = new RealmReview();
        review.setAuthor(this.author);
        review.setContent(this.content);
        return review;
    }
    public Review getReview(RealmReview review)
    {
        Review review1 = new Review();
        review1.setContent(review.getContent());
        review1.setAuthor(review.getAuthor());
        return review1;

    }
}
