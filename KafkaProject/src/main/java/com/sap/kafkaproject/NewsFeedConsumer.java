/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sap.kafkaproject;

import java.util.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.util.Random;
import com.sap.kafkaproject.HttpUrlConnection;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Manna
 */
public class NewsFeedConsumer {
    HttpUrlConnection conn;
    String sentimentAnalysisApi;
    NewsFeedConsumer(){
        conn = new HttpUrlConnection();
        sentimentAnalysisApi = "http://text-processing.com/api/sentiment/";
    }
    public void consume(){
        String topicName = "test";
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("group.id", new Random().nextInt() + "");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList(topicName));
        
        while (true) {
         ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records){
                 System.out.printf("offset = %d, key = %s, value = %s\n", 
               record.offset(), record.key(), record.value());
             try {
                 conn.sendPOST(record.value(), sentimentAnalysisApi);
             } catch (IOException ex) {
                 Logger.getLogger(NewsFeedConsumer.class.getName()).log(Level.SEVERE, null, ex);
             }
            }
              
      }     
    }
    
    public static void main(String[] args){
        NewsFeedConsumer consumer = new NewsFeedConsumer();
        consumer.consume();
    }
    
}
