package ba.unsa.etf.rma.elza_kalac.movieapp.API;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Actor;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Crew;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;


public class ActorsListResponse {

    @SerializedName("id")
    private int page;
    @SerializedName("cast")
    private List<Actor> results;
    @SerializedName("crew")
    private List<Crew> crew;

    public ActorsListResponse(int page, List<Actor> results, List<Crew> crew) {
        this.page = page;
        this.results = results;
        this.crew = crew;
    }

    public List<Actor> getResults() {
        return results;
    }

    public void setResults(List<Actor> results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }
}
