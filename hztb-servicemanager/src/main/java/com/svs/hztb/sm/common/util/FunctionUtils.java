package com.svs.hztb.sm.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FunctionUtils {
	public static <T,R> List<R> convert(List<T> source,
										Function<T,R> f) {
		List<R> result = new ArrayList<R>();
		for(T s : source) {
			result.add(f.apply(s));
		}
		return result;
	}
	public static <T, R> R convert(T source,
			Function<T,R> f) {
			R result = null;						
			result =  f.apply(source);
			return result;
	}
}
