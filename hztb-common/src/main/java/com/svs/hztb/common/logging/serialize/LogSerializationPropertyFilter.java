package com.svs.hztb.common.logging.serialize;

import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

@SuppressWarnings("rawtypes")
public class LogSerializationPropertyFilter extends SimpleBeanPropertyFilter {

	@Override
	protected boolean include(BeanPropertyWriter writer) {
		return true;
	}

	@Override
	protected boolean include(PropertyWriter writer) {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {
		Map<String, AbstractLogSerializer> fieldSerializerMap = LogSerializationFactory.getInstance().getFieldSerializerMap(pojo.getClass());
		if(!CollectionUtils.isEmpty(fieldSerializerMap)) {
			String fieldName = writer.getName();
			JsonSerializer serializer = fieldSerializerMap.get(fieldName);
			if(serializer != null) {
				Object value = ((BeanPropertyWriter) writer).get(pojo);
				if (value != null) {
					jgen.writeFieldName(writer.getName());
					serializer.serialize(value, jgen, provider);
				}
				return;
			}
 		}
		super.serializeAsField(pojo, jgen, provider, writer);
	}
	
}
