package com.svs.hztb.sm.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class FunctionUtils {
	private FunctionUtils() {

	}

	public static <T, R> List<R> convert(List<T> source, Function<T, R> f) {
		List<R> result = new ArrayList<>();
		for (T s : source) {
			result.add(f.apply(s));
		}
		return result;
	}

	public static <T, R> R convert(T source, Function<T, R> f) {
		R result;
		result = f.apply(source);
		return result;
	}
}
