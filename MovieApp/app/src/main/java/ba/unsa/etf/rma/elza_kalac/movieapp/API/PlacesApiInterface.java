package ba.unsa.etf.rma.elza_kalac.movieapp.API;

import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.PlacesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface PlacesApiInterface {

    @GET("{output}")
    Call<PlacesResponse> SearchPlaces(@Path("output") String output, @Query("key") String api_key, @Query("location") String location, @Query("radius") int radius, @Query("type") String type);
}
