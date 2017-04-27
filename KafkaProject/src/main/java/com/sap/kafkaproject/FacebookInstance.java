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
import org.json.*;
 
/**
 *
 * @author Manna
 */
public class FacebookInstance {
    String appId, appSecret, accessToken;
    Facebook facebook;
    FacebookInstance(){
        facebook = new FacebookFactory().getInstance();
        appId = "310700449350456";
        appSecret = "7efde528b03482c36a7a2e741f364222";
        accessToken = "EAACEdEose0cBAIsRFwkReOjelTlSuEh7PgMHGHYADHwmGmV3608MUGE7RJyQrGBxjOTNYZAZBnPsXxV9mS7eHGV9UyjYG41KKRr8zLiAiBOEYr0hhj7Vrw0jhOexZAfY5DwG4TuDLl4KVsbZA6cDtPSfldHLIMBE3XxUG8SSdJKvZAMQKKJrIRZCfJTm8uqqsZD";
        facebook.setOAuthAppId(appId, appSecret);
        facebook.setOAuthPermissions("read_stream");
        facebook.setOAuthAccessToken(new AccessToken(accessToken));
    }
    
    public ResponseList<Post> getLiveNewsFeed () throws FacebookException{
        return facebook.getHome();
    }
    
    public void printNewsFeed() throws FacebookException{
        List newsFeed = getLiveNewsFeed();
        Iterator iterator = newsFeed.iterator();
        
        while (iterator.hasNext()){
            Post post = (Post) iterator.next();
            String postFrom = post.getFrom().getName();
            try{
                System.out.println("NEW POST---->" + post.getMessage());
            }catch (Exception e){
                System.out.println("NEW POST EXCEPTION ---->" + iterator.next());
            }
           
        }
    } 
    
    public void perseNewsFeeds() throws FacebookException, JSONException{
        List newsFeed = getLiveNewsFeed();
        Iterator iterator = newsFeed.iterator();
        
        while (iterator.hasNext()){
            String feed = iterator.next().toString();
            JSONObject obj = new JSONObject("{" + feed + "}");            
            System.out.println("NEW POST---->"  + obj.getString("PostJSONImpl"));
        }
    }
    
}
