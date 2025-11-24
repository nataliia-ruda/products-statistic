import org.junit.jupiter.api.Test;
import product.statistics.model.Product;
import product.statistics.parser.ProductParser;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductParserTest {

    @Test
    void testParseFileReturnsProducts() throws Exception {
        ProductParser parser = new ProductParser();

        Path file = Path.of("data", "products_1.json");

        List<Product> products = parser.parseFile(file);

        assertNotNull(products, "Parsed product list must not be null");
        assertFalse(products.isEmpty(), "Product list must not be empty");

        Product first = products.get(0);
        assertNotNull(first.getName(), "Product must have a name");
        assertNotNull(first.getCategory(), "Product must have a category");
    }
}
