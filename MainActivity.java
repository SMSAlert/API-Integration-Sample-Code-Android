
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

package com.smsAlert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.TextView;

import smsAlert.SMSALERT;     /* import sms alert api  */


public class MainActivity extends AppCompatActivity {
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);


        //Create SMS ALERT object with username and password
        SMSALERT msgSmsAlert = new SMSALERT("YOUR_USERNAME_HERE","YOUR_PASSWORD_HERE");

        //If you want to use API key for authentication uncomment below line.
        //SMSALERT msgSmsAlert = new SMSALERT("YOUR_APIKEY_HERE"); //Add your SMS Alert API key

        // compose message with senderid and text-message
        msgSmsAlert.composeMessage("CVDEMO", "This Sample message body that will be sent with sender id : CVDEMO");

        // .to(String) : will send message to single mobile number
        msgSmsAlert.to("8010551055");

        // NOTE : Before calling this function you MUST call .composeMessage & .to
        String sendStatus = msgSmsAlert.send();
        text.setText("Send Status : " + sendStatus);
	    
    }
}
