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
import org.json.*;

/**
 *
 * @author Manna
 */
public class KafkaProducer {
   FacebookInstance fbInstance;
   
   KafkaProducer(){
       fbInstance = new FacebookInstance();
   }
   
   public FacebookInstance getFacebookInstance(){
       return fbInstance;
   }
   public static void main(String[] args){
       KafkaProducer producer = new KafkaProducer();
        try {
            producer.getFacebookInstance().printNewsFeed();
        } catch (FacebookException ex) {
            ex.printStackTrace();
            Logger.getLogger(KafkaProducer.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
