package ba.unsa.etf.rma.elza_kalac.movieapp.Models;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.RealmCast;
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

    public int getCast_id() {
        return cast_id;
    }

    public void setCast_id(int cast_id) {
        this.cast_id = cast_id;
    }

    public void setCharacter_name(String character_name) {
        this.character_name = character_name;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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

    public void setCast(CreditsResponse cast) {
        this.cast = cast;
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

    public String getSmallFullPosterPath(Context c) {
        if (getProfile_path() != null)
            return c.getString(R.string.MovieImageBaseAdress) + c.getString(R.string.MovieImageWidth92) + getProfile_path();
        else
            return c.getString(R.string.MovieImageBaseAdress) + c.getString(R.string.MovieImageWidth92) + getPosterPath();

    }

    public RealmCast getRealCast()
    {
        RealmCast cast = new RealmCast();
        cast.setName(this.name);
        cast.setMediaType(this.mediaType);
        cast.setCharacter_name(this.character_name);
        cast.setId(this.getId());
        return cast;
    }

    public Cast getCast(RealmCast realmCast)
    {
        Cast cast = new Cast();
        cast.setName(realmCast.getName());
        cast.setCharacter_name(realmCast.getCharacter_name());
        cast.setMediaType(realmCast.getMediaType());
        cast.setId(cast.getId());
        return cast;
    }


}
