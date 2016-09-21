package ba.unsa.etf.rma.elza_kalac.movieapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {

    //Samo mjenjaj rijec iza "movie/ i dobijas razlicita rjesenja, npr za filmove
    //top_rated, popular, now_playing, upcoming
    //npr ovdje stavljaj url sa stranice i dobijas razlicite rezultate
    //pazi da ne pocinje sa /
    @GET("movie/popular")
    Call<MovieResponse> getMostPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/now_playing")
    Call<MovieResponse> getLatestMovies(@Query("api_key") String apiKey,  @Query("page") int page);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey,  @Query("page") int page);

  //  @GET("movie/{id}")
   // Call<Movie> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

}
