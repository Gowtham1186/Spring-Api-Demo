package com.example.demo;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class HelloController {

        @GetMapping("/getResponse")
    public String getResponse(@RequestParam(name = "json1") String json1,
                              @RequestParam(name = "json2") String json2) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Decode URL-encoded strings
            String decodedJson1 = URLDecoder.decode(json1, StandardCharsets.UTF_8.toString());
            String decodedJson2 = URLDecoder.decode(json2, StandardCharsets.UTF_8.toString());

            // Parse JSON strings
            JsonNode tree1 = mapper.readTree(decodedJson1);
            JsonNode tree2 = mapper.readTree(decodedJson2);

            // Compare JSON objects
            if (tree1.equals(tree2)) {
                return "JSON objects are equal.";
            } else {
                return "JSON objects are not equal.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing JSON: " + e.getMessage();
        }
    }
}
