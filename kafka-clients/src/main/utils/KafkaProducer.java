package com.maersk.procurement.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.maersk.procurement.enums.MessageType;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.UUID;

public class KafkaProducer {
    public static String producePPMMessage(String jsonFilePath, MessageType messageType, String partitionKey) {
        Properties producerProperties = new Properties();
        try {
            producerProperties.load(Files.newBufferedReader(Paths.get("")));
            producerProperties = prepareProperties(producerProperties);
        } catch (IOException e) {
            System.err.println("Error loading Kafka producer properties:");
            e.printStackTrace();
            return null;
        }

        try (Producer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(producerProperties)) {

            String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonContent);

            String uniqueGuid = UUID.randomUUID().toString();
            String ppmId = String.valueOf(System.currentTimeMillis());
            System.out.println("Generated GUID: " + uniqueGuid); // Debugging to verify uniqueness
            System.out.println("Generated ppmId: " + ppmId);
            JsonNode headerNode = rootNode.get("header");
            if (headerNode != null && headerNode.isObject()) {
                ((ObjectNode) headerNode).put("eventId", uniqueGuid);
                JsonNode dataNode = rootNode.get("data");
                ((ObjectNode) dataNode).put("ppmId", ppmId);
            } else {
                System.err.println("Header field not found in the JSON.");
            }

            String updatedJsonContent = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);

            ProducerRecord<String, String> record = new ProducerRecord<>(messageType.getTopicName(), partitionKey, updatedJsonContent);
            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    System.out.printf("Message sent to topic: %s, partition: %d, offset: %d%n",
                            metadata.topic(), metadata.partition(), metadata.offset());
                } else {
                    System.err.println("Error sending message:");
                    exception.printStackTrace();
                }
            });

            return ppmId;
        } catch (IOException e) {
            System.err.println("Error reading JSON file:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error producing message:");
            e.printStackTrace();
        }
        return null;
    }

}
