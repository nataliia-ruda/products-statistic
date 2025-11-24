package product.statistics.writer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class XmlFileWriter {

    public void writeStatistics(String attributeName,
                                List<Map.Entry<String, Long>> sortedStats,
                                Path outputPath) throws IOException {

        String fileName = "statistics_by_" + attributeName + ".xml";
        Path targetFile = outputPath.resolve(fileName);

        StringBuilder sb = new StringBuilder();
        sb.append("<statistics>\n");
        for (Map.Entry<String, Long> entry : sortedStats) {
            sb.append("  <item>\n");
            sb.append("    <value>").append(entry.getKey()).append("</value>\n");
            sb.append("    <count>").append(entry.getValue()).append("</count>\n");
            sb.append("  </item>\n");
        }
        sb.append("</statistics>\n");

        Files.createDirectories(outputPath);

        Files.writeString(targetFile, sb.toString(), StandardCharsets.UTF_8);

        System.out.println("XML file written to: " + targetFile.toAbsolutePath());
    }
}
