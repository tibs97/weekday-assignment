package service;

import dto.Order;

import java.util.*;
import java.util.stream.Collectors;

import static constants.Constants.MAX_COOKING_SLOTS;

public class WaitTimeCalculator {

  private Double waitTime = 0d;
  private List<Double> cookingArray = new ArrayList<>(MAX_COOKING_SLOTS);

  /**
   * Calculates the wait time for the order with the given order-id.
   *
   * @param orderId       Order id of the order whose wait time is to be calculated.
   * @param orders        List of all orders.
   * @param skippedOrders Set of order ids of the order that are denied.
   * @return Wait time of the order with the given order id.
   */
  public Double computeWaitTime(final int orderId, final List<Order> orders,
      final Set<Integer> skippedOrders) {
    for (Order order : orders) {
      if (skippedOrders.contains(order.getOrderId())) {
        continue;
      }
      int slotsRequired = order.getSlotsRequired();
      while (slotsRequired > getFreeSlots()) {
        Collections.sort(cookingArray);
        Double min = cookingArray.get(0);
        cookingArray = cookingArray.stream().map(x -> x - min).filter(x -> x != 0)
            .collect(Collectors.toList());
        waitTime += min;
      }
      if (order.getOrderId() == orderId) {
        return waitTime;
      }
      for (int i = 0; i < slotsRequired; i++) {
        cookingArray.add(order.getAbsoluteDeliveryTime());
      }
    }
    throw new RuntimeException(String.format(
        "There was some issue while calculating wait time for OrderId - %d, Orders - %s", orderId,
        orders));
  }

  private int getFreeSlots() {
    return MAX_COOKING_SLOTS - cookingArray.size();
  }

}
