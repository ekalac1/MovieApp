package ba.unsa.etf.rma.elza_kalac.movieapp.API;

import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
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
    Call<MoviesListResponse> getSearchedMovies(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page );

   @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey, @Query("append_to_response") String append);

    @GET("movie/{id}/credits")
    Call<CreditsResponse> getActors(@Path("id") int id, @Query("api_key") String apiKey );

    @GET("movie/{id}/reviews")
    Call<ReviewResponse> getReview(@Path("id") int id, @Query("api_key") String apiKey);

}
