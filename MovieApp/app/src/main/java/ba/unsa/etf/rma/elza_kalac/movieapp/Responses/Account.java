package ba.unsa.etf.rma.elza_kalac.movieapp.Responses;


import com.google.gson.annotations.SerializedName;

public class Account {
    @SerializedName("username")
    private String username;
    @SerializedName("id")
    private int accountId;

    public String getUsername() {return username;}

    public int getAccountId() {return accountId;}
}

