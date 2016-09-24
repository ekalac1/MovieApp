package ba.unsa.etf.rma.elza_kalac.movieapp.API;

import ba.unsa.etf.rma.elza_kalac.movieapp.Models.MovieDeta;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("movie/popular")
    Call<MoviesListResponse> getMostPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/now_playing")
    Call<MoviesListResponse> getLatestMovies(@Query("api_key") String apiKey,  @Query("page") int page);

    @GET("movie/top_rated")
    Call<MoviesListResponse> getTopRatedMovies(@Query("api_key") String apiKey,  @Query("page") int page);

    @GET("search/movie")
    Call<MoviesListResponse> getSearchedMovies(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page);

   @GET("movie/{id}")
    Call<MovieDeta> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

}
