package ba.unsa.etf.rma.elza_kalac.movieapp.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Review;

/**
 * Created by Laptop on 28.09.2016..
 */
public class ReviewResponse {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("results")
    @Expose
    private List<Review> results;

    public ReviewResponse(int id, int page, List<Review> results) {
        this.id = id;
        this.page = page;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }
}
