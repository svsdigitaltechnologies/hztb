package com.svs.hztb.sm.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;

public final class DateUtils {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(DateUtils.class);

	private DateUtils() {

	}

	public static Date getDate(String dateStr) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {

			date = formatter.parse(dateStr);
			return date;
		} catch (ParseException e) {
			LOGGER.error("Error occured while parsing date {}", e);
		}
		return date;
	}
}
