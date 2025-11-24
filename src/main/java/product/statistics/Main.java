package product.statistics;

import product.statistics.model.Product;
import product.statistics.parser.ProductParser;
import product.statistics.statistics.StatisticsCalculator;
import product.statistics.writer.XmlFileWriter;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

        Path folderPath;
        String attribute;

        if (args.length < 2) {
            System.out.println("Usage: java -jar product-statistics.jar <folderPath> <attributeName>");
            System.out.println("Example: java -jar product-statistics.jar data category");
            System.out.println("No arguments provided");
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

        ProductParser parser = new ProductParser();
        StatisticsCalculator calculator = new StatisticsCalculator();
        XmlFileWriter xmlWriter = new XmlFileWriter();


        List<Path> jsonFiles = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath, "*.json")) {
            for (Path filePath : stream) {
                jsonFiles.add(filePath);
            }
        }

        System.out.println("Found " + jsonFiles.size() + " JSON files.");


        int threadCount = 4;

        System.out.println("Using thread pool with " + threadCount + " threads...");

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<Future<List<Product>>> futures = new ArrayList<>();

        long startTime = System.currentTimeMillis();


        for (Path filePath : jsonFiles) {
            Future<List<Product>> future = executor.submit(() -> {
                System.out.println("Parsing in thread " + Thread.currentThread().getName() +
                        " file: " + filePath);
                return parser.parseFile(filePath);
            });
            futures.add(future);
        }


        List<Product> allProducts = new ArrayList<>();
        for (Future<List<Product>> future : futures) {
            List<Product> productsFromFile = future.get();
            allProducts.addAll(productsFromFile);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        executor.shutdown();

        System.out.println("Total products: " + allProducts.size());
        System.out.println("Parsing and collecting time: " + duration + " ms");


        Map<String, Long> stats = calculator.calculate(allProducts, attribute);


        List<Map.Entry<String, Long>> sorted = new ArrayList<>(stats.entrySet());
        sorted.sort((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()));

        System.out.println("Statistics by " + attribute + " (all files):");
        for (Map.Entry<String, Long> entry : sorted) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }


        Path outputFolder = Path.of("output");
        xmlWriter.writeStatistics(attribute, sorted, outputFolder);

        System.out.println("XML file was created!");
    }
}


