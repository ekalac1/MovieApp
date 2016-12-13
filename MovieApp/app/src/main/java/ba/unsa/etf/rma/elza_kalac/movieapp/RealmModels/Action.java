package ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels;


import io.realm.RealmObject;

public class Action extends RealmObject {
    private int id;
    private String mediaType;
    private String actionType;
    private int vote;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }
}
