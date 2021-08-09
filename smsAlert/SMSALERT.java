
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

    String mainUrl = "https://www.smsalert.co.in/api/push.json?";
    String otpUrl = "https://www.smsalert.co.in/api/mverify.json?";
    String apikey, user, pwd, auth, text, sender, mobileno, msgSchedule, route, code;
    boolean isFlash = false, isUnicode = false, debugMode = false;
    String debugTAG = "SMSALERT";
    String response;
    String template = "Hello, Your OTP is [otp]";

    //api
    public SMSALERT(String key) {
        apikey = key;
    }

	//api debug
    public SMSALERT(String key, boolean debug) {
        apikey = key;
        debugMode = debug;
    }

    //Authentication using username and password
    public SMSALERT(String username,String password) {
       user = username;
       pwd = password;
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

    public void setauth(){
        if (apikey != null)
            auth ="&apikey=" + apikey;
        else
            auth ="&user=" + user+ "&pwd=" + pwd;
    }

    //get xml string
    public String getXML() throws UnsupportedEncodingException{
        setauth();

        String finalLink = mainUrl + auth + "&sender=" + sender + "&mobileno=" + mobileno + "&text=" + URLEncoder.encode(text, "UTF-8");
        
        return finalLink;
    }

    //get xml string
    public String getotpXML() throws UnsupportedEncodingException{
        setauth();

        String finalLink = otpUrl + auth + "&sender=" + sender + "&mobileno=" + mobileno + "&template=" + URLEncoder.encode(template, "UTF-8");

        return finalLink;
    }

    //get xml string
    public String setotpXML() throws UnsupportedEncodingException{
        setauth();

        String finalLink = otpUrl + auth + "&mobileno=" + mobileno + "&code=" + code;

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

    //send otp function
    public String sendOTP() {
        try {
            return execute("SENDOTP").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "Exception: " + e;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return "Exception: " + e;
        }
    }

    //verify otp function
    public String verifyOTP(String code) {
        try {
            return execute("VERIFYOTP").get();
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

                case "SENDOTP":
                    String sendotp = getotpXML();
                    if (!sendotp.contains("ERROR")) {
                        myURL = new URL(sendotp);
                    } else {
                        return sendotp;
                    }
                    break;

                case "VERIFYOTP":
                    String verifyotp = setotpXML();
                    if (!verifyotp.contains("ERROR")) {
                        myURL = new URL(verifyotp);
                    } else {
                        return verifyotp;
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
