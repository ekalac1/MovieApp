package ba.unsa.etf.rma.elza_kalac.movieapp.Responses;


import com.google.gson.annotations.SerializedName;

public class PostResponse {

    @SerializedName("status_code")
    private int statusCode;
    @SerializedName("status_message")
    private String statusMessage;

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
