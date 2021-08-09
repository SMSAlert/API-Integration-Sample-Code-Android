# SMS ALERT SDK for Android (Beta)

Easy way to integrate [SMS ALERT](https://smsalert.co.in/api) API's to your Android App.

## ABOUT SMS ALERT
SMS ALERT, a bulk SMS service provider offers transactional, nondnd and promotional bulk SMS solutions to enterprises via powerful & robust bulk SMS gateway internationally.


## Usage

#### Create SMS ALERT object with your Username and Password or your [API KEY](https://www.smsalert.co.in/api). 

Using API key: 
```java
SMSALERT msgSmsAlert = new SMSALERT("API_KEY_HERE");
```   
	
Using Username and Password:
```java
SMSALERT msgSmsAlert = new SMSALERT("YOUR_USERNAME_HERE","YOUR_PASSWORD_HERE");
```

##### After creating SMS ALERT's Object, below are the methods provides
NOTE : These methods return String response which is received from the REST API's of SMS ALERT
	 
To send SMS Mobile Number
```java
msgSmsAlert.composeMessage("CVDEMO", "This Sample message body that will be sent with sender id : CVDEMO");

msgSmsAlert.to("8010551055");
        
String sendStatus = msgSmsAlert.send();
text.setText("Send Status : " + sendStatus);
```
	
To send OTP
```java
String sendStatus = msgSmsAlert.sendOTP();
text.setText("Send Status : " + sendStatus);
```
	
To verify OTP
```java
String sendStatus = msgSmsAlert.verifyOTP("OTP_HERE");
text.setText("Send Status : " + sendStatus);
```


## License

    Copyright 2016 Cozy Vision Pvt. Ltd.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0
