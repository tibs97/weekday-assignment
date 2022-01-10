import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.MealType;
import dto.Order;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        String inputFile = "C:\\Users\\tibsanity\\Documents\\Projects\\weekday-assignment\\input.txt";
        List<Order> orders = new Gson().fromJson(new FileReader(inputFile), new TypeToken<List<Order>>() {
        }.getType());
        System.out.println(orders);
    }
}