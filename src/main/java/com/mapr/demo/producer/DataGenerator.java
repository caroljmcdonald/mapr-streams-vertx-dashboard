package com.mapr.demo.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Simple class to generate ECG data and post it to a stream
 */
public class DataGenerator {

    // Declare a new producer
    public static KafkaProducer producer;


    // Set the default stream and topic to publish to.
    private static String topic;
    private static String fileName;


    public static void main(String[] args) throws IOException, InterruptedException {

        if (args.length != 2) {
            throw new IllegalArgumentException("Must have the topic and file parameter :  DataGenerator /apps/iot_stream:ecg /data/ecg.tsv ");
        }
        topic = args[0];
        fileName = args[1];

        System.out.println("Sending to topic " + topic);
        configureProducer();
        ArrayList<String> lines = null;
        Data data = null;

        try {
            lines = (ArrayList) Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
            ArrayList<Double> colAPointsList = new ArrayList<Double>();
            ArrayList<Double> colBPointsList = new ArrayList<Double>();
            String previousValue = null;
            for (String line : lines) {
                String[] xValuesArr = line.split(",");
                if (previousValue == null) {
                    previousValue = xValuesArr[2];
                }
                if (previousValue.equals(xValuesArr[2])) {
                    colAPointsList.add(Double.parseDouble(xValuesArr[0]));
                    colBPointsList.add(Double.parseDouble(xValuesArr[1]));
                } else {
                    data = new Data(xValuesArr[2], colAPointsList.toArray(new Double[colAPointsList.size()]), colBPointsList.toArray(new Double[colBPointsList.size()]));
                    ObjectMapper mapper = new ObjectMapper();
                    String dataStr = null;
                    try {
                        dataStr = mapper.writeValueAsString(data);
                    } catch (JsonProcessingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                          System.out.println(dataStr);
                    producer.send(new ProducerRecord<String, String>(topic, data.getxValue(), dataStr));
                    try {
                        Thread.sleep(3000l);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    colAPointsList.clear();
                    colBPointsList.clear();
                    colAPointsList.add(Double.parseDouble(xValuesArr[0]));
                    colBPointsList.add(Double.parseDouble(xValuesArr[1]));
                    previousValue = xValuesArr[2];
                }
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        producer.close();
        System.out.println("All done.");
        System.exit(1);

    }

    /**
     * MapR Streams Producer configuration
     */
    private static void configureProducer() {
        Properties props = new Properties();

        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<>(props);
    }

}
