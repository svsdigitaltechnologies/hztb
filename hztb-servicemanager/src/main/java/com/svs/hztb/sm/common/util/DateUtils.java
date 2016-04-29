package com.svs.hztb.sm.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static Date getDate(String dateStr) {
		Date date = null;
		try {

				date = formatter.parse(dateStr);
				return date;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		}
	}
