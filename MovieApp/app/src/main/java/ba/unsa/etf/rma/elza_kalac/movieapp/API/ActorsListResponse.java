package ba.unsa.etf.rma.elza_kalac.movieapp.API;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Actor;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;


public class ActorsListResponse {

    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<Actor> results;
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

    public List<Actor> getResults() {
        return results;
    }

    public void setResults(List<Actor> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
