package com.svs.hztb.common.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.svs.hztb.common.exception.SystemError;
import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;

public final class StringUtil {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(StringUtil.class);

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private StringUtil() {

	}

	static {
		MAPPER.setSerializationInclusion(Include.NON_NULL);
		MAPPER.configure(SerializationFeature.INDENT_OUTPUT, false);
	}

	public static String spelReplacePayload(String payload, Map<String, Object> context) {
		String result = payload;
		if (result == null) {
			return result;
		}
		Set<String> spelParameters = determineSpelParameters(payload);
		if (spelParameters != null) {
			for (String spel : spelParameters) {
				String adjustedSpel = spel.replace("[", "['").replace("]", "']");
				String replacement = getValueFromContext(adjustedSpel, context);
				if (replacement == null) {
					throw new SystemError(String.format("Failed tp convert SPEL paramert %s", spel));
				}
				result = result.replace("{" + spel + "}", replacement);
			}
		}
		return result;
	}

	private static Set<String> determineSpelParameters(String payload) {
		Set<String> spelParameters = null;
		String regex = Pattern.quote("{") + "(.*?)" + Pattern.quote("}");
		Pattern p = Pattern.compile(regex, Pattern.DOTALL);
		Matcher matcher = p.matcher(payload);
		while (matcher.find()) {
			if (spelParameters == null) {
				spelParameters = new HashSet<>();
			}
			String spel = matcher.group(1);
			spelParameters.add(spel);
		}
		return spelParameters;
	}

	public static String getValueFromContext(String spel, Map<String, Object> context) {
		try {
			ExpressionParser parser = new SpelExpressionParser();
			Expression expression = parser.parseExpression(spel);
			EvaluationContext evaluationContext = new StandardEvaluationContext(context);
			Object value = expression.getValue(evaluationContext);
			if (value == null) {
				return null;
			}
			return value.toString();
		} catch (Exception exception) {
			LOGGER.error("Exception while attempting to resolve SPEL expression: {}, {}", spel, exception);
			throw exception;
		}
	}

	public static String convertObjectToJSON(Object object) {
		try {
			return MAPPER.writeValueAsString(object);
		} catch (Exception exception) {
			LOGGER.error("Error converting to JSON : {}", exception);
			return StringUtils.EMPTY;
		}
	}

	public static String base64Encode(String value) {
		return base64Encode(getUTF8Bytes(value));
	}

	public static byte[] getUTF8Bytes(String value) {
		return value.getBytes(Charsets.UTF_8);
	}

	public static String base64Encode(byte[] value) {
		return Base64.encodeBase64String(value);
	}
}
