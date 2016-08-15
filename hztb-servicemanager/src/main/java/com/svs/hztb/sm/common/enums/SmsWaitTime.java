package com.svs.hztb.sm.common.enums;

/**
 * enum for sms wait time
 * 
 * @author skairamk
 *
 */
public enum SmsWaitTime {

	SMS_SENT_1(1, 60), SMS_SENT_2(2, 7200), SMS_SENT_3(3, 28800), SMS_SENT_4(4, 57600);

	private final Integer sentCount;
	private final Integer waitTime;

	private SmsWaitTime(Integer sentCount, Integer waitTime) {
		this.sentCount = sentCount;
		this.waitTime = waitTime;
	}

	public Integer getSentCount() {
		return sentCount;
	}

	public Integer getWaitTime() {
		return waitTime;
	}

}
