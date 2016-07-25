package com.svs.hztb.sm.common.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;

public final class JsonUtil {
	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(JsonUtil.class);

	private JsonUtil() {

	}

	public static String toJson(Object object) {
		String jsonString = "";
		ObjectMapper mapper = new ObjectMapper();
		Writer strWriter = new StringWriter();
		try {
			mapper.writeValue(strWriter, object);
			jsonString = strWriter.toString();

		} catch (IOException e) {
			LOGGER.error("Error occured while toJson {}", e);
		}
		return jsonString;
	}
}
