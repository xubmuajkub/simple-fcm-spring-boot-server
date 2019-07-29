package com.doubttech.fcmhttpserver.controller;

import java.io.IOException;

import com.doubttech.fcmhttpserver.service.AndroidPushNotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

	@Autowired
	AndroidPushNotificationsService androidPushNotificationsService;

	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public ResponseEntity<String> send() {
		try {
			String pushNotification = androidPushNotificationsService.sendPushNotification();
			return new ResponseEntity<>(pushNotification, HttpStatus.OK);
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
		return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
	}
}