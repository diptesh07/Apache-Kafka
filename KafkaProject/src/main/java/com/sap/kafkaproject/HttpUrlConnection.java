/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sap.kafkaproject;

import java.io.*;
import java.net.*;  
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;
/**
 *
 * @author Manna
 */
public class HttpUrlConnection {
    private static final String POST_URL = " http://text-processing.com/api/sentiment/";
    private static String POST_PARAM;
    
    HttpUrlConnection(String feed){
        POST_PARAM = "text=" + feed;
    }
    
    private static void sendPOST() throws IOException {
		URL obj = new URL(POST_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");

		// For POST only - START
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(POST_PARAM.getBytes());
		os.flush();
		os.close();
		// For POST only - END
		int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
                        
			// print result
			
                    try {
                        JSONObject jsonObj = new JSONObject(response.toString());
                        String label = jsonObj.getString("label");
                        System.out.println(label);
                    } catch (JSONException ex) {
                        Logger.getLogger(HttpUrlConnection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        
		} else {
			System.out.println("POST request not worked");
		}
	}
    
    public static void main(String[] args){
        HttpUrlConnection conn = new HttpUrlConnection("i hate biryani");
        try {
            conn.sendPOST();
        } catch (IOException ex) {
            Logger.getLogger(HttpUrlConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
