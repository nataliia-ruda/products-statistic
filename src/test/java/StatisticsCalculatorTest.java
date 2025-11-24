import org.junit.jupiter.api.Test;
import product.statistics.model.Product;
import product.statistics.statistics.StatisticsCalculator;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatisticsCalculatorTest {

    @Test
    void testCalculateByCategory() {
        Product p1 = new Product();
        p1.setCategory("Electronics");

        Product p2 = new Product();
        p2.setCategory("Electronics");

        Product p3 = new Product();
        p3.setCategory("Food");

        List<Product> products = List.of(p1, p2, p3);

        StatisticsCalculator calculator = new StatisticsCalculator();
        Map<String, Long> stats = calculator.calculate(products, "category");

        assertEquals(2L, stats.get("Electronics"));
        assertEquals(1L, stats.get("Food"));
    }

    @Test
    void testCalculateTags() {
        Product p1 = new Product();
        p1.setTags("eco, wireless");

        Product p2 = new Product();
        p2.setTags("wireless, gaming");

        Product p3 = new Product();
        p3.setTags("eco");

        List<Product> products = List.of(p1, p2, p3);

        StatisticsCalculator calculator = new StatisticsCalculator();
        Map<String, Long> stats = calculator.calculate(products, "tags");

        assertEquals(2L, stats.get("eco"));
        assertEquals(2L, stats.get("wireless"));
        assertEquals(1L, stats.get("gaming"));
    }
}
