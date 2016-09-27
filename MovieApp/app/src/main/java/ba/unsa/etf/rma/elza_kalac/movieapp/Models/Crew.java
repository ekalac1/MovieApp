package ba.unsa.etf.rma.elza_kalac.movieapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Laptop on 27.09.2016..
 */
public class Crew {

    @SerializedName("credit_id")
    @Expose
    private int id;
    @SerializedName("department")
    @Expose
    private String department;

    public Crew(int id, String department) {
        this.id = id;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
