package com.webdev.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.LinkedTreeMap;

public class DoubleToIntDeserializer implements JsonDeserializer<Map<String, Object>> {

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		return (Map<String, Object>) read(json);
	}

	public Object read(JsonElement jsonElement) {

		if (jsonElement.isJsonArray()) {
			List<Object> list = new ArrayList<Object>();
			JsonArray jsonArray = jsonElement.getAsJsonArray();
			for (JsonElement anArr : jsonArray) {
				list.add(read(anArr));
			}
			return list;
		}

		if (jsonElement.isJsonObject()) {
			Map<String, Object> map = new LinkedTreeMap<String, Object>();
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			Set<Map.Entry<String, JsonElement>> entitySet = jsonObject.entrySet();
			for (Map.Entry<String, JsonElement> entry : entitySet) {
				map.put(entry.getKey(), read(entry.getValue()));
			}
			return map;
		}

		if (jsonElement.isJsonPrimitive()) {
			JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
			if (jsonPrimitive.isBoolean()) {
				return jsonPrimitive.getAsBoolean();
			}

			if (jsonPrimitive.isString()) {
				return jsonPrimitive.getAsString();
			}

			if (jsonPrimitive.isNumber()) {

				Number num = jsonPrimitive.getAsNumber();

				if (Math.ceil(num.doubleValue()) == num.longValue())
					return num.longValue();

				return num.doubleValue();

			}
		}

		return null;
	}

}
