package ba.unsa.etf.rma.elza_kalac.movieapp.Responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Cast;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Crew;


public class CreditsResponse {

    @SerializedName("cast")
    private List<Cast> cast;
    public List<Cast> getCast() {return cast; }
    @SerializedName("crew")
    private List<Crew> crew;
    @SerializedName("guest_stars")
    private List<Cast> guestStars;

    public String getDirectors()
    {
        String director = "";
        for (Crew c: crew)
        {
            if (c.getJob().equals("Director"))
                director=director+c.getName();
        }
        return director;
    }
    public String getWriters()
    {
        String writers="";
        for (Crew c:crew)
        {
            if (c.getJob().equals("Screenplay"))
                writers=writers+c.getName()+", ";
        }
        if (writers.length()!=0)
            return writers.substring(0, writers.length()-2);
            else return "";
    }

    public String getStars()
    {
        String stars="";
        for (int i=0; i<3 & i<cast.size(); i++)
        {
            stars=stars+cast.get(i).getName()+", ";
        }

        if (stars.length()!=0)
            return stars.substring(0, stars.length()-2);
        else return "";
    }

    public List<Cast> getEpisodeCast()
    {
        List<Cast> result=cast;
        result.addAll(guestStars);
        return result;
    }


}
