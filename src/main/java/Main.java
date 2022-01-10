import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.Order;
import service.DeliveryTimeCalculationService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

public class Main {

  public static void main(String[] args) throws FileNotFoundException {

    String inputFile = "input.txt";

    List<Order> orders = parseOrdersFromInput(inputFile);

    Map<Order, Optional<Double>> deliveryTimes = new DeliveryTimeCalculationService().computeDeliveryTimesForConcurrentOrders(
        orders);

    stdout(orders, deliveryTimes);

  }

  private static void stdout(List<Order> orders, Map<Order, Optional<Double>> times) {
    for (Order order : orders) {
      if (times.get(order).isPresent()) {
        System.out.printf("Order %d will get delivered in %.1f minutes%n", order.getOrderId(),
            times.get(order).get());
      } else {
        System.out.printf("Order %d is denied because the restaurant cannot accommodate it.%n",
            order.getOrderId());
      }
    }
  }

  private static List<Order> parseOrdersFromInput(String inputFile) throws FileNotFoundException {
    return new Gson().fromJson(new FileReader(inputFile),
        new TypeToken<List<Order>>() {
        }.getType());
  }
}