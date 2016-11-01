package ba.unsa.etf.rma.elza_kalac.movieapp.Models;


import com.google.gson.annotations.SerializedName;

public class Account {
    @SerializedName("username")
    private String username;
    @SerializedName("id")
    private int accountId;
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUsername() {return username;}

    public int getAccountId() {return accountId;}

    public void setUsername(String username) {this.username=username;}
}

