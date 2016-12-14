package ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import io.realm.RealmObject;


public class RealmCast extends RealmObject {

    @SerializedName("character")
    @Expose
    private String character_name;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profile_path")
    @Expose
    private String profile_path;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("homepage")
    private String homePage;
    @SerializedName("biography")
    private String biography;
    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String posterPath;
 @SerializedName("media_type")
 private String mediaType;



    public String getMediaType(){return mediaType;}

    public void setCharacter_name(String character_name) {
        this.character_name = character_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
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
