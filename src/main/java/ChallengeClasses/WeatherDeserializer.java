package ChallengeClasses;

import ChallengeClasses.Metrics.Metrics;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class WeatherDeserializer {

    private final ObjectMapper objectMapper;

    public WeatherDeserializer() {
        this.objectMapper = new ObjectMapper();
    }

    public List<Record> parseJsonFile(String filePath) throws IOException {
        InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream(filePath);
        JsonNode rootNode = objectMapper.readTree(is);
        ArrayNode recordsArray = (ArrayNode) rootNode.get("records");

        List<Record> recordList = new ArrayList<>();

        for (JsonNode recordNode : recordsArray) {
            if (recordNode.size() != 20) continue;
            Record record = createRecordFromArray(recordNode);
            recordList.add(record);
        }

        return recordList;
    }

    private Record createRecordFromArray(JsonNode recordArray) {
        // Extract metadata fields
        String id = recordArray.get(0).asText();
        String devId = recordArray.get(1).asText();
        String name = recordArray.get(2).asText();
        String location = recordArray.get(3).asText();
        String dateTimeStr = recordArray.get(5).asText();
        String dayOfWeekStr = recordArray.get(8).asText();

        // Create Metadata object
        Metadata metadata = new Metadata(id, devId, name, location, dateTimeStr, dayOfWeekStr);

        // Extract metrics fields
        Double airTemp = parseDoubleSafely(recordArray.get(9));
        Double atmosphericPressure = parseDoubleSafely(recordArray.get(10));
        Double gustSpeed = parseDoubleSafely(recordArray.get(11));
        Double precipitation = parseDoubleSafely(recordArray.get(12));
        Double relativeHumidity = parseDoubleSafely(recordArray.get(13));
        Double solar = parseDoubleSafely(recordArray.get(14));
        Double strikeDistance = parseDoubleSafely(recordArray.get(15));
        Double strikes = parseDoubleSafely(recordArray.get(16));
        Double vapourPressure = parseDoubleSafely(recordArray.get(17));
        Double windDirection = parseDoubleSafely(recordArray.get(18));
        Double windSpeed = parseDoubleSafely(recordArray.get(19));

        // Create Metrics object
        Metrics metrics = new Metrics(
                airTemp, atmosphericPressure, gustSpeed, precipitation,
                relativeHumidity, solar, strikeDistance, strikes,
                vapourPressure, windDirection, windSpeed
        );

        // Create and return Record
        Record record = new Record();
        record.setMetadataInfo(metadata);
        record.setMetrics(metrics);

        return record;
    }

    private Double parseDoubleSafely(JsonNode node) {
        if (node == null || node.isNull()) {
            return null;
        }
        try {
            return node.asDouble();
        } catch (Exception e) {
            return null;
        }
    }
}