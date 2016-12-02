package ba.unsa.etf.rma.elza_kalac.movieapp.Models;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import ba.unsa.etf.rma.elza_kalac.movieapp.R;

public class Image {

    @SerializedName("file_path")
    private String filePath;

    public String getFilePath() {
        return filePath;
    }
    public String getFullPosterPath(Context c) {;
        return c.getString(R.string.MovieImageBaseAdress)+c.getString(R.string.MovieImageWidth500) + getFilePath();
    }
}
