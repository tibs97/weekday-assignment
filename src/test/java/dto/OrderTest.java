package dto;

import constants.MealType;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static constants.MealType.*;

public class OrderTest {

  @Test(dataProvider = "orderUpdateAttributesTestDP")
  public void orderUpdateAttributesTest(MealType[] meals, Double distance, int expectedSlots,
      Double expectedTime) {
    Order o = new Order();
    o.setMeals(meals);
    o.setDistance(distance);
    o.updateAttributes();
    Assert.assertEquals(o.getSlotsRequired(), expectedSlots);
    Assert.assertEquals(o.getAbsoluteDeliveryTime(), expectedTime);
  }

  @DataProvider(name = "orderUpdateAttributesTestDP")
  public Object[][] orderUpdateAttributesTestDP() {
    MealType[] m1 = {APPETIZER, APPETIZER, APPETIZER};
    MealType[] m2 = {MAIN_COURSE, MAIN_COURSE, MAIN_COURSE};
    MealType[] m3 = {APPETIZER, MAIN_COURSE, APPETIZER};
    MealType[] m4 = {APPETIZER};
    MealType[] m5 = {MAIN_COURSE};

    Double d1 = 10d;
    Double d2 = 20d;
    Double d3 = 30d;
    Double d4 = 40d;
    Double d5 = 50d;

    int es1 = 3;
    int es2 = 6;
    int es3 = 4;
    int es4 = 1;
    int es5 = 2;

    Double et1 = 97d;
    Double et2 = 189d;
    Double et3 = 269d;
    Double et4 = 337d;
    Double et5 = 429d;

    return new Object[][]{
        {m1, d1, es1, et1},
        {m2, d2, es2, et2},
        {m3, d3, es3, et3},
        {m4, d4, es4, et4},
        {m5, d5, es5, et5}
    };
  }
}
