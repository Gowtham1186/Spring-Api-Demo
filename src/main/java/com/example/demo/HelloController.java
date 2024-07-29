package com.example.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api")
public class HelloController {

    	    @PostMapping("/receiveJsonArrays")
	    public String receiveJsonArrays(@RequestBody JsonNode requestBody) {
	        try {
	            // Extract two JSON arrays from the request body
	            JsonNode jsonArray1 = requestBody.get("array1");
	            JsonNode jsonArray2 = requestBody.get("array2");

	            // Compare JSON objects
	            if (jsonArray1.equals(jsonArray2)) {
	                return "JSON objects are equal.";
	            } else {
	                return "JSON objects are not equal.";
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Error processing JSON: " + e.getMessage();
	        }
	    
}
