package service;

import dto.Order;

import java.util.*;

import static constants.Constants.MAX_COOKING_SLOTS;
import static constants.Constants.MAX_DELIVERY_TIME;

public class DeliveryTimeCalculationService {

  /**
   * Calculates the delivery times for all orders that are received at the same time.
   *
   * @param orders List of all orders.
   * @return Map - Key->Order, Value->Optional containing Delivery time. Optional will be empty in
   * case order is denied.
   */
  public Map<Order, Optional<Double>> computeDeliveryTimesForConcurrentOrders(List<Order> orders) {
    preprocess(orders);
    return findDeliveryTimeForAllOrders(orders);
  }

  private Map<Order, Optional<Double>> findDeliveryTimeForAllOrders(final List<Order> orders) {
    Map<Order, Optional<Double>> deliveryTimes = new HashMap<>();
    Set<Integer> deniedOrders = new HashSet<>();

    for (Order order : orders) {
      Optional<Double> optionalTotalDeliveryTime = computeTotalDeliveryTimeForAnOrder(order, orders,
          deniedOrders);
      if (optionalTotalDeliveryTime.isEmpty()) {
        //order got denied, hence optional empty was returned.
        deniedOrders.add(order.getOrderId());
      }
      deliveryTimes.put(order, optionalTotalDeliveryTime);
    }
    return deliveryTimes;
  }

  /**
   * Calculates Total Delivery Time for an order. Total Delivery Time = Waiting Time in Queue +
   * Absolute Delivery Time
   *
   * @param order        Order to computer total delivery time for
   * @param orders       List of all Orders
   * @param deniedOrders Set of OrderIds of orders denied before current order.
   * @return Optional containing total delivery time for current order. Optional empty in case order
   * is denied.
   */
  public Optional<Double> computeTotalDeliveryTimeForAnOrder(final Order order,
      final List<Order> orders, final Set<Integer> deniedOrders) {
    Double absoluteDeliveryTime = order.getAbsoluteDeliveryTime();
    int slotsRequired = order.getSlotsRequired();
    if (!orderIsValid(slotsRequired, absoluteDeliveryTime)) {
      return Optional.empty();
    }
    Double waitTime = new WaitTimeCalculator().computeWaitTime(order.getOrderId(), orders,
        deniedOrders);
    Double totalDeliveryTime = absoluteDeliveryTime + waitTime;
    return totalDeliveryTime <= MAX_DELIVERY_TIME ? Optional.of(totalDeliveryTime)
        : Optional.empty();
  }

  /**
   * Validate an order based on slots required and absolute delivery time
   *
   * @param slotsRequired        Slots required to cook the order
   * @param absoluteDeliveryTime Absolute Delivery Time for the order (Total Delivery Time - Wait
   *                             Time)
   * @return True - Order is valid and can be processed. False - Order cannot be processed.
   */
  private boolean orderIsValid(int slotsRequired, Double absoluteDeliveryTime) {
    return slotsRequired <= MAX_COOKING_SLOTS && absoluteDeliveryTime <= MAX_DELIVERY_TIME;
  }

  /**
   * Iterate through all orders to update compute and set the attributes - SlotsRequired and
   * AbsoluteDeliveryTime, since these are required in calculating the total delivery time.
   *
   * @param orders Orders List.
   */
  private void preprocess(List<Order> orders) {
    orders.forEach(Order::updateAttributes);
  }

}
