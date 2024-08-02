package com.example.demo;

import java.util.Iterator;
import java.util.TreeMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class HelloController {
	public static TreeMap<String, String> hashMap=null;
	@PostMapping("/receiveJsonArrays")
	public TreeMap<String, String> receiveJsonArrays(@RequestBody String requestBody) {
		try {
			ObjectMapper mapper=new ObjectMapper();
	        // Convert JSON string to JSONObject
	        JsonNode jsonNode= mapper.readTree(requestBody);
			
			if(jsonNode.isObject()) {
	        JSONObject jsonObject=new JSONObject(requestBody);
	        hashMap=new TreeMap<String, String>();
				generateSchema(jsonObject, "", 0);
			}else {
			// Convert the string to a JSONArray
			JSONArray array = new JSONArray(requestBody);
			hashMap=new TreeMap<String, String>();
			// Iterate through each JSONObject in the JSONArray
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				generateSchema(object, "[" + i + "]", 0);
			}
			}
			return hashMap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private static void generateSchema(JSONObject jsonObject, String prefix, int index) throws JSONException {
		@SuppressWarnings("unchecked")
		Iterator<String> keys = jsonObject.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			Object value = jsonObject.get(key);
			String newPrefix = prefix.isEmpty() ? key : prefix + "/" + key;

			if (value instanceof JSONObject) {
				System.out.println(newPrefix + " : " + value);
				generateSchema((JSONObject) value, newPrefix, index);
			} else if (value instanceof JSONArray) {
				JSONArray array = (JSONArray) value;
				if (array.length() > 0) {
					for (int i = 0; i < array.length(); i++) {
						Object firstElement = array.get(i);
						if (firstElement instanceof JSONObject) {
							generateSchema((JSONObject) firstElement, newPrefix + "[" + i + "]", i);
						} else {
							hashMap.put(newPrefix, (String) firstElement);
						}
					}
				} else {
					hashMap.put(newPrefix, " : []");
				}
			} else {
				String result="";
				if(!value.equals(""))
					result=value.toString();
				hashMap.put(newPrefix, result);
			}
		}
	}
}
