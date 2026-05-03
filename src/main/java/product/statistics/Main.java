package product.statistics;

import product.statistics.service.ProductService;
import product.statistics.writer.XmlFileWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {

        Path folderPath;
        String attribute;

        if (args.length < 2) {
            System.out.println("Usage: java -jar product-statistics.jar <folderPath> <attributeName>");
            System.out.println("Example: java -jar product-statistics.jar data category");
            System.out.println("No arguments provided, using defaults.");
            folderPath = Path.of("data");
            attribute = "category";
        } else {
            folderPath = Path.of(args[0]);
            attribute = args[1];
        }

        if (!attribute.equals("category") && !attribute.equals("brand") && !attribute.equals("tags")) {
            System.out.println("Unknown attribute: " + attribute);
            System.out.println("Valid attributes are: category, brand, tags");
            return;
        }

        try {
            ProductService service = new ProductService();
            Map<String, Long> stats = service.processFiles(folderPath, attribute);

            List<Map.Entry<String, Long>> sorted = new ArrayList<>(stats.entrySet());
            sorted.sort((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()));

            System.out.println("Statistics by " + attribute + ":");
            for (Map.Entry<String, Long> entry : sorted) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }

            XmlFileWriter xmlWriter = new XmlFileWriter();
            xmlWriter.writeStatistics(attribute, sorted, Path.of("output"));
            System.out.println("XML file was created!");

        } catch (IOException e) {
            System.err.println("File error: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Processing interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            System.err.println("Error processing file: " + e.getCause().getMessage());
        }
    }
}
