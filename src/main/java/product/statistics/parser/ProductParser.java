package product.statistics.parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import product.statistics.model.Product;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProductParser {

    private final ObjectMapper jsonMapper;

    public ProductParser() {
        this.jsonMapper = new ObjectMapper();
    }

    public List<Product> parseFile(Path filePath) throws IOException {
        List<Product> products = new ArrayList<>();

        try (JsonParser parser = jsonMapper.getFactory().createParser(filePath.toFile())) {

            if (parser.nextToken() != JsonToken.START_ARRAY) {
                throw new IOException("Expected JSON array in file: " + filePath);
            }

            while (parser.nextToken() == JsonToken.START_OBJECT) {
                Product product = jsonMapper.readValue(parser, Product.class);
                products.add(product);
            }
        }

        return products;
    }
}
