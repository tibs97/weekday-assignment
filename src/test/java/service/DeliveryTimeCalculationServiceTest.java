package service;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import dto.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.internal.collections.Pair;

public class DeliveryTimeCalculationServiceTest {

  @Test(dataProvider = "dp")
  public void computeDeliveryTimesForConcurrentOrders_ShouldReturnDeliveryTimeMap_whenOrdersArrayIsProvided(
      List<Order> orders, Map<Order, Optional<Double>> expectedMap) {
    orders.forEach(Order::updateAttributes);
    Map<Order, Optional<Double>> actualMap = new DeliveryTimeCalculationService().computeDeliveryTimesForConcurrentOrders(
        orders);
    Assert.assertEquals(actualMap, expectedMap);
  }

  @DataProvider(name = "dp")
  public Object[][] dataProvider() {
    List<Object[]> objects = new ArrayList<>();
    String testCases = "{\"testcase1\":{\"orders\":[{\"orderId\":12,\"meals\":[\"A\",\"A\"],\"distance\":5},{\"orderId\":21,\"meals\":[\"A\",\"M\"],\"distance\":1},{\"orderId\":14,\"meals\":[\"M\",\"M\",\"M\",\"M\",\"A\",\"A\",\"A\"],\"distance\":10},{\"orderId\":32,\"meals\":[\"M\"],\"distance\":0.1},{\"orderId\":22,\"meals\":[\"A\"],\"distance\":3}],\"resultMap\":{\"12\":{\"value\":57},\"14\":{},\"21\":{\"value\":37},\"22\":{\"value\":70.8},\"32\":{\"value\":29.8}}},\"testcase2\":{\"orders\":[{\"orderId\":12,\"meals\":[\"A\",\"A\"],\"distance\":5},{\"orderId\":21,\"meals\":[\"A\",\"M\"],\"distance\":1},{\"orderId\":14,\"meals\":[\"M\",\"M\"],\"distance\":10},{\"orderId\":32,\"meals\":[\"M\"],\"distance\":0.1},{\"orderId\":22,\"meals\":[\"A\"],\"distance\":3}],\"resultMap\":{\"12\":{\"value\":57},\"14\":{\"value\":146},\"21\":{\"value\":37},\"22\":{\"value\":98},\"32\":{\"value\":86.8}}},\"testcase3\":{\"orders\":[{\"orderId\":12,\"meals\":[\"A\",\"A\"],\"distance\":5},{\"orderId\":21,\"meals\":[\"A\",\"M\"],\"distance\":1},{\"orderId\":14,\"meals\":[\"M\",\"M\"],\"distance\":10},{\"orderId\":32,\"meals\":[\"M\"],\"distance\":0.1},{\"orderId\":22,\"meals\":[\"A\"],\"distance\":3},{\"orderId\":69,\"meals\":[\"A\",\"M\"],\"distance\":3}],\"resultMap\":{\"12\":{\"value\":57},\"14\":{\"value\":146},\"21\":{\"value\":37},\"22\":{\"value\":98},\"32\":{\"value\":86.8},\"69\":{}}}}";
    Map<String, LinkedTreeMap> testCaseMap = new Gson().fromJson(testCases,
        new TypeToken<Map<String, Object>>() {
        }.getType());
    for (String s : testCaseMap.keySet()) {
      Pair<List<Order>, Map<Order, Optional<Double>>> data = parseOrdersAndExpectedMap(
          testCaseMap.get(s));
      objects.add(new Object[]{data.first(), data.second()});
    }
    Object[][] data = new Object[objects.size()][2];
    data[0] = objects.get(0);
    data[1] = objects.get(1);
    data[2] = objects.get(2);
    return data;
  }

  private Pair<List<Order>, Map<Order, Optional<Double>>> parseOrdersAndExpectedMap(
      LinkedTreeMap o) {
    List<Order> orders = new Gson().fromJson(new Gson().toJson(o.get("orders")),
        new TypeToken<List<Order>>() {
        }.getType());
    Map<String, Optional<Double>> map = new Gson().fromJson(new Gson().toJson(o.get("resultMap")),
        new TypeToken<Map<String, Optional<Double>>>() {
        }.getType());
    return new Pair<>(orders, orders.stream().collect(Collectors.toMap(x -> x, x -> map.get(
        String.valueOf(x.getOrderId())))));
  }

}
