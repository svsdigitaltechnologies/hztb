package com.svs.hztb.api.common.utils;

import java.io.IOException;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.svs.hztb.common.model.GCMMessageNotification;

public class GCMMessageNotificationClient {

	private StringBuilder CLASS_NAME = new StringBuilder(this.getClass().getSimpleName());

	private static final String GOOGLE_SERVER_KEY = "AIzaSyCsC0hpjniljcP8esEzB8V_PuMyS6cba0Q";
	private static final String MESSAGE_KEY = "message";

	public Result pushMessageNotification(GCMMessageNotification gcmMessageNotification) throws IOException {

		Sender sender = new Sender(GOOGLE_SERVER_KEY);
		Result result = null;
		Message message = new Message.Builder().timeToLive(30).delayWhileIdle(true)
				.addData(MESSAGE_KEY, gcmMessageNotification.getMessage()).build();
		System.out.println("regId: " + gcmMessageNotification.getGcmRegistrationID());
		try {
			result = sender.send(message, gcmMessageNotification.getGcmRegistrationID(), 1);
			System.out.println(result.toString());
		} catch (IOException e) {
			throw e;
		}
		return result;
	}

	/**
	 * @param messageId
	 * @param userBO
	 * @throws HowZThisBuddySystemException
	 */
	public void processPushNotification(String gcmRegistrationID, String message) throws IOException {
		StringBuilder METHOD_NAME = CLASS_NAME.append("processPushNotification");

		Result result = pushMessageNotification(new GCMMessageNotification(gcmRegistrationID, message));

	}
}
