/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sap.kafkaproject;

import facebook4j.*;
import facebook4j.auth.AccessToken;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sap.kafkaproject.FacebookInstance;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.json.*;
import org.apache.kafka.clients.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * @author Manna
 */
public class NewsFeedProducer {
   FacebookInstance fbInstance;
   String topicName;
   Properties props;
   Producer <String, String> producer;
   NewsFeedProducer(){
       fbInstance = new FacebookInstance();
       topicName = "test";
       props = new Properties();
       props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
       props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
       props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
       producer = new KafkaProducer<String, String>(props);
   }
   
   public FacebookInstance getFacebookInstance(){
       return fbInstance;
   }
  
   public void publish() throws FacebookException{
       int batchCount = 0;
       List<Post> liveNewsFeed = fbInstance.getLiveNewsFeed(); 
       Iterator iterator = liveNewsFeed.iterator();
       while(iterator.hasNext()){
           Post post = (Post) iterator.next();
           producer.send(new ProducerRecord<String, String> (topicName, post.getMessage()));
           System.out.println("NEW POST ---->" + post.getMessage());
           System.out.println("Successfully produced batch = " + batchCount++);
       }
   }
   
   public static void main(String[] args){
       NewsFeedProducer newsFeedProducer = new NewsFeedProducer();
        try {
            while(true){
                newsFeedProducer.publish();
            }
        } catch (FacebookException ex) {
            ex.printStackTrace();
            Logger.getLogger(KafkaProducer.class.getName()).log(Level.SEVERE, null, ex);
        } 
   }
}
