package ba.unsa.etf.rma.elza_kalac.movieapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hugsby on 05-Dec-16.
 */

public class Place {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("geometry")
    private Geometry geometry;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Geometry getGeometry() {
        return geometry;
    }
}
