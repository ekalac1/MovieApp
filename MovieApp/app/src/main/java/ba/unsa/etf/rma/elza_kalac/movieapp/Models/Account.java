package ba.unsa.etf.rma.elza_kalac.movieapp.Models;


import com.google.gson.annotations.SerializedName;

public class Account {
    @SerializedName("username")
    private String username;
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private int accountId;
    private String sessionId;


    public String getName(){ return name; }

    public void setName(String name) {this.name=name;}

    public void setAccountId(int accountId) {this.accountId=accountId;}

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

