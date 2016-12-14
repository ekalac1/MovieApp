package ba.unsa.etf.rma.elza_kalac.movieapp.Responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Place;

/**
 * Created by Hugsby on 05-Dec-16.
 */

public class PlacesResponse {
    //TODO: Implement list od places
    @SerializedName("results")
    private List<Place> results;

    public List<Place> getResults() {
        return results;
    }
}
