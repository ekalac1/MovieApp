package ba.unsa.etf.rma.elza_kalac.movieapp.API;

import ba.unsa.etf.rma.elza_kalac.movieapp.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String API_KEY = BuildConfig.API_KEY;
    public static final String YOUTUBE_API_KEY = BuildConfig.YOUTUBE_API_KEY;

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
