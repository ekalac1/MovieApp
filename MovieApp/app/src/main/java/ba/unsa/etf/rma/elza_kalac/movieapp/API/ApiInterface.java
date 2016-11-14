package ba.unsa.etf.rma.elza_kalac.movieapp.API;

import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Cast;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Episode;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Season;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.TvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Account;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.AuthentificationResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.MoviesListResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.PostResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.SearchResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.TvShowResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("movie/popular")
    Call<MoviesListResponse> getMostPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/now_playing")
    Call<MoviesListResponse> getLatestMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/top_rated")
    Call<MoviesListResponse> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey, @Query("append_to_response") String append);

    @GET("tv/popular")
    Call<TvShowResponse> getPopularTvShows(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("tv/airing_today")
    Call<TvShowResponse> getAiringTodayTvShows(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("tv/on_the_air")
    Call<TvShowResponse> getLatestTvShows(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("tv/top_rated")
    Call<TvShowResponse> getHighestRatedTvShows(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("search/multi")
    Call<SearchResponse> getSearchedItems(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page);

    @GET("tv/{tv_id}")
    Call<TvShow> getTvShowDetails(@Path("tv_id") int id, @Query("api_key") String apiKey, @Query("append_to_response") String append);

    @GET("tv/{tv_id}/season/{season_number}")
    Call<Season> getSeasonEpisodes(@Path("tv_id") int id, @Path("season_number") int seasonNumber, @Query("api_key") String apiKey);

    @GET("person/{person_id}")
    Call<Cast> getActor(@Path("person_id") int id, @Query("api_key") String apiKey, @Query("append_to_response") String append);

    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}")
    Call<Episode> getEpisode(@Path("tv_id") int tvID, @Path("season_number") int seasonNumber, @Path("episode_number") int episodeNumber, @Query("api_key") String apiKey, @Query("append_to_response") String append);

    @GET("authentication/token/new")
    Call<AuthentificationResponse> getToken(@Query("api_key") String apiKey);

    @GET("authentication/token/validate_with_login")
    Call<AuthentificationResponse> Login(@Query("api_key") String apiKey, @Query("username") String username, @Query("password") String password, @Query("request_token") String requestToken);

    @GET("authentication/session/new")
    Call<AuthentificationResponse> CreateSession(@Query("api_key") String apiKey, @Query("request_token") String requestToken);

    @GET("account")
    Call<Account> GetAccount(@Query("api_key") String apiKey, @Query("session_id") String session_id);

    @GET("account/{account_id}/favorite/movies")
    Call<MoviesListResponse> getFavoritesMovies(@Path("account_id") Integer accountId, @Query("api_key") String apiKey, @Query("session_id") String session_id, @Query("sort_by") String sortBy);

    @GET("account/{account_id}/watchlist/movies")
    Call<MoviesListResponse> getMoviesWatchList(@Path("account_id") Integer accountId, @Query("api_key") String apiKey, @Query("session_id") String session_id, @Query("sort_by") String sortBy);

    @GET("account/{account_id}/rated/movies")
    Call<MoviesListResponse> getMoviesRatings(@Path("account_id") Integer accountId, @Query("api_key") String apiKey, @Query("session_id") String session_id, @Query("sort_by") String sortBy);

    @GET("account/{account_id}/favorite/tv")
    Call<TvShowResponse> getFavoritesTvShows(@Path("account_id") Integer accountId, @Query("api_key") String apiKey, @Query("session_id") String session_id,  @Query("sort_by") String sortBy);

    @GET("account/{account_id}/watchlist/tv")
    Call<TvShowResponse> getTvShowWatchList(@Path("account_id") Integer accountId, @Query("api_key") String apiKey, @Query("session_id") String session_id, @Query("sort_by") String sortBy);

    @GET("account/{account_id}/rated/tv")
    Call<TvShowResponse> getTvShowRatings(@Path("account_id") Integer accountId, @Query("api_key") String apiKey, @Query("session_id") String session_id,  @Query("sort_by") String sortBy);

    @POST("account/{account_id}/favorite")
    Call<PostResponse> PostFavorite(@Path("account_id") Integer accountId, @Query("api_key") String apiKey, @Query("session_id") String session_id, @Body PostBody body);

    @POST("account/{account_id}/watchlist")
    Call<PostResponse> MarkWatchList(@Path("account_id") Integer accountId, @Query("api_key") String apiKey, @Query("session_id") String session_id, @Body PostBody body);

    @POST("movie/{movie_id}/rating")
    Call<PostResponse> RateMovie(@Path("movie_id") Integer movieID, @Query("api_key") String apiKey, @Query("session_id") String session_id, @Body PostBody body);

    @POST("tv/{tv_id}/rating")
    Call<PostResponse> RateTvshow(@Path("tv_id") Integer tvShowID, @Query("api_key") String apiKey, @Query("session_id") String session_id, @Body PostBody body);

    @GET("discover/movie")
    Call<MoviesListResponse> DiscoverMovies(@Query("api_key") String apiKey, @Query("sort_by") String sortBy, @Query("primary_release_date.gte") String primaryRelaseDate, @Query("primary_release_date.lte") String endDate);

}
