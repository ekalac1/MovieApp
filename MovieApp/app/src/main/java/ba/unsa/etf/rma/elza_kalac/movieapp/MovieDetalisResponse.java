package ba.unsa.etf.rma.elza_kalac.movieapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.MoviesDetailsPackage.MovieDetalis;

/**
 * Created by Laptop on 22.09.2016..
 */
public class MovieDetalisResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private MovieDetalis results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public MovieDetalis getResults() {
        return results;
    }
}
