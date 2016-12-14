
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

package com.smsAlert;  /* import sms alert package  */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.smsAlert.R;
import com.smsAlert.SMSALERT;     /* import sms alert api  */


public class MainActivity extends AppCompatActivity {
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);

        /* Create SMS ALERT object with your Auth Key */
        SMSALERT msgSmsAlert = new SMSALERT("your_auth_key");

		// compose message with senderid and text-message
        msgSmsAlert.composeMessage("SENDERID", "This Sample message body that will be sent with sender id : SENDERID");

        // .to(String) : will send message to single mobile number
        msgSmsAlert.to("9718603323";

        // NOTE : Before calling this function you MUST call .composeMessage & .to
        String sendStatus = msgSmsAlert.send();
        text.setText("Send Status : " + sendStatus);
      
    }
}
