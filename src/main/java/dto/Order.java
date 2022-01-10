package dto;

import lombok.Data;

@Data
public class Order {

    private int orderId;
    private MealType[] meals;
    private Double distance;

}
