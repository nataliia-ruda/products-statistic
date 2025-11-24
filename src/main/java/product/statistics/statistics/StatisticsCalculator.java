package product.statistics.statistics;

import product.statistics.model.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsCalculator {


    public Map<String, Long> calculate(List<Product> products, String attributeName) {
        Map<String, Long> counts = new HashMap<>();

        for (Product product : products) {
            switch (attributeName) {
                case "category" -> countSingleValue(counts, product.getCategory());
                case "brand" -> countSingleValue(counts, product.getBrand());
                case "tags" -> countTags(counts, product.getTags());
                default -> throw new IllegalArgumentException(
                        "Unsupported attribute: " + attributeName
                );
            }
        }

        return counts;
    }

    private void countSingleValue(Map<String, Long> counts, String value) {
        if (value == null || value.isBlank()) {
            return;
        }
        counts.put(value, counts.getOrDefault(value, 0L) + 1);
    }

    private void countTags(Map<String, Long> counts, String tagsString) {
        if (tagsString == null || tagsString.isBlank()) {
            return;
        }

        String[] tags = tagsString.split(",");
        for (String rawTag : tags) {
            String tag = rawTag.trim();
            if (!tag.isEmpty()) {
                counts.put(tag, counts.getOrDefault(tag, 0L) + 1);
            }
        }
    }
}

