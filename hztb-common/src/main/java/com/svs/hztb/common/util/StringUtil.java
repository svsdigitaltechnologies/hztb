package com.svs.hztb.common.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.svs.hztb.common.exception.SystemException;

public class StringUtil {
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
					throw new SystemException(String.format("Failed tp convert SPEL paramert %s", spel));
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
			throw exception;
		}
	}
}
