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
        accessToken = "EAACEdEose0cBAGC5cDtY8FJRY3dDdkGU8BlR3gm9mEZCXJpOQ1gMZAFlrjxBTNB4RNLdLmrNMNAFF1gyGRsuIrF1v1bV5MZAdqPOb98E7pQ7m8upqDEKtUjtA2lT115pOp43izNd6hkHSQKbxje2xrVxkF8GHlmAPl0lXtY4KgkUZCxNILoOnY415dTfKHYZD";
        facebook.setOAuthAppId(appId, appSecret);
        facebook.setOAuthAccessToken(new AccessToken(accessToken));
        facebook.setOAuthPermissions("read_stream");
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
