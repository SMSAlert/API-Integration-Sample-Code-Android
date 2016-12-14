
/*
 * Copyright 2016 Cozy Vision Pvt. Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.smsAlert;  /* import sms alert api package  */

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Cozy Vision Pvt. Ltd.
 */

public class SMSALERT extends AsyncTask<String, Void, String> {

    String mainUrl = "http://www.smsalert.co.in/api/push.json?";
    String apikey, text, sender, mobileno, msgSchedule, route;
    boolean isFlash = false, isUnicode = false, debugMode = false;
    String debugTAG = "SMSALERT";
    String response;

    //api
    public SMSALERT(String key) {
        apikey = key;
    }

	//api debug
    public SMSALERT(String key, boolean debug) {
        apikey = key;
        debugMode = debug;
    }

	//compose message
    public void composeMessage(String id, String message) {
        sender = id;
        text = message;
    }

    //compose mobile
    public void to(String mobile) {
        mobileno = mobile;
    }
   
    //flas if needed
    public void flash(boolean trueORfalse) {
        isFlash = trueORfalse;
    }

	//unicode if needed
    public void unicode(boolean trueORfalse) {
        isUnicode = trueORfalse;
    }

	//set route
    public void setRoute(String route) {
        route = route;
    }

    //get xml string
    public String getXML() {
        StringBuilder xmlData = new StringBuilder("<MESSAGE>");
        xmlData.append("<AUTHKEY>").append(apikey).append("</AUTHKEY>");

        // ROUTE
        if (route != null)
            xmlData.append("<ROUTE>").append(route).append("</ROUTE>");
      
        // SENDER ID : Cannot be Null
        if (sender != null)
            xmlData.append("<SENDER>").append(sender).append("</SENDER>");
        else
            return "ERROR : Sender ID is Missing";

        //FLASH
        if (isFlash)
            xmlData.append("<FLASH>").append("1").append("</FLASH>");

       
        // TEXT : Cannot be Null
        if (text != null)
            xmlData.append("<SMS TEXT=\"").append(text).append("\">");
        else
            return "ERROR : Text Message is Missing";

        // TO : Cannot be null
        if (mobileno != null) {
            if (mobileno != null) {
                if (mobileno.length() == 10)
                    xmlData.append("<ADDRESS TO=\"").append(mobileno).append("\"></ADDRESS>");
                else
                    return "ERROR : " + mobileno + " is not a valid Mobile Number";
            }

            xmlData.append("</SMS>").append("</MESSAGE>");
        } else
            return "ERROR : Mobile Number(s) Missing";

        String finalLink = null;
        try {
            finalLink = mainUrl + URLEncoder.encode(xmlData.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return finalLink;
    }

	//send function
    public String send() {
        try {
            return execute("SEND").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "Exception: " + e;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return "Exception: " + e;
        }
    }
	

    @Override
    protected String doInBackground(String... params) {
        try {

            URLConnection myURLConnection;
            URL myURL = null;
            BufferedReader reader;


            String command = params[0];
            switch (command) {
                case "SEND":
                    String url = getXML();
                    if (!url.contains("ERROR")) {
                        myURL = new URL(url);
                    } else {
                        return url;
                    }
                    break;
               
            }

            if (myURL != null) {
                if (debugMode)
                    Log.d(debugTAG, "URL: " + myURL.toString());
                myURLConnection = myURL.openConnection();
                myURLConnection.connect();
                reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
                String lineReader;
                while ((lineReader = reader.readLine()) != null)
                    response = lineReader;
                if (debugMode)
                    Log.d(debugTAG, "RESPONSE: " + response);
                reader.close();

                if (response.contains("error")) {
                    if (response.contains("error"))
                        return "Message not send.";
                    else if (response.contains("success"))
                        return "Message sent successfully";
                    else
                        return response;
                }
            } else
                response = "ERROR (NULL) : URL is NULL";

            return response;

        } catch (IOException e) {
            if (debugMode)
                return "ERROR (IOException) : Unable to download the requested page.\n";
            else
                return "ERROR (IOException) : \n" + e;
        }
    }
}
