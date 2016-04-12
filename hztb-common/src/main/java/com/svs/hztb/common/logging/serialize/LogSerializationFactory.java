package com.svs.hztb.common.logging.serialize;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.common.collect.ImmutableMap;
import com.svs.hztb.common.exception.SystemException;

@SuppressWarnings("rawtypes")
public class LogSerializationFactory {
	private static final LogSerializationFactory instance = new LogSerializationFactory();

	private ObjectWriter objectWriter;
	private Map<Class<?>, Map<String, AbstractLogSerializer>> classSerializeMap = new ConcurrentHashMap<>();
	
	private LogSerializationFactory() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		objectMapper.addMixIn(Object.class, LogSerializationJsonFilterMixIn.class);
		FilterProvider filters = new SimpleFilterProvider().addFilter("alter output of filtered properties", new LogSerializationPropertyFilter());
		objectWriter = objectMapper.writer(filters);
	}

	public static LogSerializationFactory getInstance() {
		return instance;
	}
	
	public Map<String, AbstractLogSerializer> getFieldSerializerMap(Class<? extends Object> jsonClass) {
		Map<String, AbstractLogSerializer> fieldSerializerMap = classSerializeMap.get(jsonClass);
		if (fieldSerializerMap == null) {
			fieldSerializerMap = buildFieldSerializerMap(classSerializeMap, jsonClass);
		}
		return fieldSerializerMap;
	}
	
	public final void addLogSerializerConfig(Class<?> clazz, Pair<String, AbstractLogSerializer> ... fieldSerializerMap) {
		ImmutableMap.Builder<String, AbstractLogSerializer> builder = ImmutableMap.<String, AbstractLogSerializer> builder();
		for (Pair<String, AbstractLogSerializer> pair : fieldSerializerMap) {
			builder.put(pair.getLeft(), pair.getRight());
		}
		classSerializeMap.put(clazz, builder.build());
	}
	
	public String getJsonString(Object json) {
		if (json == null) {
			return null;
		}
		try {
			return objectWriter.writeValueAsString(json);
		} catch (JsonProcessingException exception) {
			return String.format("unable to log json class: %s", json.getClass().getName());
		}
	}
	
	private Map<String, AbstractLogSerializer> buildFieldSerializerMap(Map<Class<?>, Map<String, AbstractLogSerializer>> classSerializerMap, Class<?> jsonClass) {
		Map<String, AbstractLogSerializer> fieldSerializerMap = new HashMap<>();
		// get all declared fields
		
		Field[] fields = jsonClass.getDeclaredFields();
		for (Field field : fields) {
			processField(fieldSerializerMap, field);
		}
		classSerializerMap.put(jsonClass, fieldSerializerMap);
		return fieldSerializerMap;
	}
	
	@SuppressWarnings("unchecked")
	private void processField(Map<String, AbstractLogSerializer> fieldSerializerMap, Field field) {
		for (Annotation annotation : field.getAnnotations()) {
			if (annotation.getClass().getInterfaces()[0].getSimpleName().contains("LogSerializer")) {
				try {
					Method method = annotation.getClass().getMethod("getSerializerClass");
					Class<AbstractLogSerializer> serializerClass = (Class<AbstractLogSerializer>)method.invoke(annotation);
					AbstractLogSerializer<?, ?>serializer = serializerClass.newInstance();
					serializer.setAnnotation(annotation);
					fieldSerializerMap.put(field.getName(), serializer);
				} catch(Exception e) {
					throw new SystemException("Unable to inject custom log serializer", e);
				}
			}
		}
	}
}
