package ba.unsa.etf.rma.elza_kalac.movieapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Laptop on 17.09.2016..
 */
public interface ApiInterface {

    //Samo mjenjaj rijec iza "movie/ i dobijas razlicita rjesenja, npr za filmove
    //top_reted, popular, now_playing, upcoming
    //npr ovdje stavljaj url sa stranice i dobijas razlicite rezultate
    //pazi da ne pocinje sa /
    @GET("movie/popular")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

  //  @GET("movie/{id}")
   // Call<Movie> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}
