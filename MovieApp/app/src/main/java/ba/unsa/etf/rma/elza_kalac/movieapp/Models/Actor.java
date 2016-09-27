package ba.unsa.etf.rma.elza_kalac.movieapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Actor {

    @SerializedName("cast_id")
    @Expose
    private int cast_id;
    @SerializedName("character")
    @Expose
    private String character_name;
    @SerializedName("credit_id")
    @Expose
    private int credit_id;
   @SerializedName("id")
   @Expose
    private int id;
   @SerializedName("name")
   @Expose
   private String name;
   @SerializedName("order")
   @Expose
   private int order;
   @SerializedName("profile_path")
   @Expose
   private String profile_path;


   public void setCast_id(int cast_id) {
      this.cast_id = cast_id;
   }

   public void setCharacter_name(String character_name) {
      this.character_name = character_name;
   }

   public void setCredit_id(int credit_id) {
      this.credit_id = credit_id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setOrder(int order) {
      this.order = order;
   }

   public void setProfile_path(String profile_path) {
      this.profile_path = profile_path;
   }

   public Actor(int cast_id, String character_name, int credit_id, int id, String name, int order, String profile_path) {
      this.cast_id = cast_id;
      this.character_name = character_name;
      this.credit_id = credit_id;
      this.id = id;
      this.name = name;
      this.order = order;
      this.profile_path = profile_path;
   }

   public int getCast_id() {
      return cast_id;
   }

   public String getCharacter_name() {
      return character_name;
   }

   public int getCredit_id() {
      return credit_id;
   }

   public int getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public int getOrder() {
      return order;
   }

   public String getProfile_path() {
      return profile_path;
   }
}
