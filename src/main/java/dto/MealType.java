package dto;

import com.google.gson.annotations.SerializedName;

public enum MealType {

    @SerializedName("A")
    APPETIZER(17.0, 1),
    @SerializedName("M")
    MAIN_COURSE(29.0, 2);

    private final Double cookingTime;
    private final int slotsRequired;

    MealType(Double cookingTime, int slotsRequired) {
        this.cookingTime = cookingTime;
        this.slotsRequired = slotsRequired;
    }
}
