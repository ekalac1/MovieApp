package ba.unsa.etf.rma.elza_kalac.movieapp.Models;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.StreamHandler;

import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.CreditsResponse;


public class Cast {

    @SerializedName("cast_id")
    @Expose
    private int cast_id;
    @SerializedName("character")
    @Expose
    private String character_name;
    @SerializedName("credit_id")
    @Expose
    private String credit_id;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("order")
    @Expose
    private int order;
    @SerializedName("profile_path")
    @Expose
    private String profile_path;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("homepage")
    private String homePage;
    @SerializedName("biography")
    private String biography;
    @SerializedName("combined_credits")
    private CreditsResponse cast;
    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String posterPath;
 @SerializedName("media_type")
 private String mediaType;



    public String getMediaType(){return mediaType;}



    public CreditsResponse getCast() {
        return cast;
    }

    public String getHomePage() {
        return homePage;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getBiography() {
        return biography;
    }

    public String getTitle() {
        return title;
    }

    public String getCharacter_name() {
        return character_name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getFullPosterPath(Context c) {
        if (getProfile_path() != null)
            return c.getString(R.string.MovieImageBaseAdress) + c.getString(R.string.MovieImageWidth500) + getProfile_path();
        else
            return c.getString(R.string.MovieImageBaseAdress) + c.getString(R.string.MovieImageWidth500) + getPosterPath();

    }

    public String getSmallFullPosterPath(Context c) {
        if (getProfile_path() != null)
            return c.getString(R.string.MovieImageBaseAdress) + c.getString(R.string.MovieImageWidth92) + getProfile_path();
        else
            return c.getString(R.string.MovieImageBaseAdress) + c.getString(R.string.MovieImageWidth92) + getPosterPath();

    }


}
