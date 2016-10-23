package ba.unsa.etf.rma.elza_kalac.movieapp.Responses;


import com.google.gson.annotations.SerializedName;

public class AuthentificationResponse {
    @SerializedName("success")
    private boolean success;
    @SerializedName("request_toker")
    private String requestToken;

    public boolean isSuccess() {
        return success;
    }

    public String getRequestToken() {
        return requestToken;
    }
}
