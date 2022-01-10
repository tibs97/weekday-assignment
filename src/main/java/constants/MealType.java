package constants;

import com.google.gson.annotations.SerializedName;

public enum MealType {

  @SerializedName("A")
  APPETIZER(17d, 1),
  @SerializedName("M")
  MAIN_COURSE(29d, 2);

  private final Double cookingTime; //minutes
  private final int slotsRequired;

  MealType(Double cookingTime, int slotsRequired) {
    this.cookingTime = cookingTime;
    this.slotsRequired = slotsRequired;
  }

  public int getSlotsRequired() {
    return slotsRequired;
  }

  public Double getCookingTime() {
    return cookingTime;
  }
}
