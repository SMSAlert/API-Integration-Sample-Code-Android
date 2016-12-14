# SMS ALERT SDK for Android (Beta)

Easy way to integrate [SMS ALERT](https://smsalert.co.in/api) API's to your Android App.

## ABOUT SMS ALERT
SMS ALERT, a bulk SMS service provider offers transactional, nondnd and promotional bulk SMS solutions to enterprises via powerful & robust bulk SMS gateway internationally.


## Usage

#### Create SMS ALERT object with you [API KEY](http://www.smsalert.co.in/api)

	Normal Use: 
	```java
	 SMSALERT msgSmsAlert = new SMSALERT("your_auth_key");
	```    

	##### After creating SMS ALERT's Object, below are the methods provides
	NOTE : These methods return String response which is received from the REST API's of SMS ALERT
	 
	To send SMS Mobile Number
	```java

	msgSmsAlert.composeMessage("SENDERID", "This Sample message body that will be sent with sender id : SENDERID");

	msgSmsAlert.to("9718603323";

	String sendStatus = msgSmsAlert.send();
	text.setText("Send Status : " + sendStatus);


## License

    Copyright 2016 Cozy Vision Pvt. Ltd.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

