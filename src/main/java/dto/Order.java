package dto;

import constants.MealType;
import lombok.Data;

import java.util.Arrays;

import static constants.Constants.DELIVERY_SPEED;
import static constants.MealType.APPETIZER;
import static constants.MealType.MAIN_COURSE;

@Data
public class Order {

  private int orderId;
  private MealType[] meals;
  private Double distance; //kilometers

  /**
   * Number of slots required to cook an order
   */
  private int slotsRequired;

  /**
   * Absolute Delivery Time = Time to Cook + Time to Deliver
   * Basically, time required for an order to get delivered after it has started preparing.
   */
  private Double absoluteDeliveryTime;

  public void updateAttributes() {
    updateSlotsRequired();
    updateAbsoluteDeliveryTime();
  }

  private void updateSlotsRequired() {
    slotsRequired = Arrays.stream(meals).mapToInt(MealType::getSlotsRequired).sum();
  }

  private void updateAbsoluteDeliveryTime() {
    absoluteDeliveryTime = cookingTime() + deliveryTime();
  }

  private Double deliveryTime() {
    return DELIVERY_SPEED * distance;
  }

  private Double cookingTime() {
    if (Arrays.asList(meals).contains(MAIN_COURSE)) {
      return MAIN_COURSE.getCookingTime();
    }
    return APPETIZER.getCookingTime();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Order order = (Order) o;

    return orderId == order.orderId;
  }

  @Override
  public int hashCode() {
    return orderId;
  }
}
