package product.statistics.service;

import product.statistics.model.Product;
import product.statistics.parser.ProductParser;
import product.statistics.statistics.StatisticsCalculator;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class ProductService {

    public static final int DEFAULT_THREAD_COUNT = 4;

    private final int threadCount;
    private final ProductParser parser;
    private final StatisticsCalculator calculator;

    public ProductService() {
        this(DEFAULT_THREAD_COUNT);
    }

    public ProductService(int threadCount) {
        this.threadCount = threadCount;
        this.parser = new ProductParser();
        this.calculator = new StatisticsCalculator();
    }

    public Map<String, Long> processFiles(Path folderPath, String attribute)
            throws IOException, InterruptedException, ExecutionException {

        List<Path> jsonFiles = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath, "*.json")) {
            for (Path filePath : stream) {
                jsonFiles.add(filePath);
            }
        }

        System.out.println("Found " + jsonFiles.size() + " JSON files.");
        System.out.println("Using thread pool with " + threadCount + " threads...");

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<Future<List<Product>>> futures = new ArrayList<>();

        for (Path filePath : jsonFiles) {
            Future<List<Product>> future = executor.submit(() -> {
                System.out.println("Parsing in thread " + Thread.currentThread().getName()
                        + " file: " + filePath);
                return parser.parseFile(filePath);
            });
            futures.add(future);
        }

        List<Product> allProducts = new ArrayList<>();
        for (Future<List<Product>> future : futures) {
            allProducts.addAll(future.get());
        }

        executor.shutdown();

        System.out.println("Total products: " + allProducts.size());

        return calculator.calculate(allProducts, attribute);
    }

    public int getThreadCount() {
        return threadCount;
    }
}
