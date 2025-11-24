package product.statistics.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import product.statistics.model.Product;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class ProductParser {

    private final ObjectMapper jsonMapper;

    public ProductParser() {
        this.jsonMapper = new ObjectMapper();
        this.jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public List<Product> parseFile(Path filePath) throws IOException {

        Product[] products = jsonMapper.readValue(filePath.toFile(), Product[].class);
        return Arrays.asList(products);
    }
}
