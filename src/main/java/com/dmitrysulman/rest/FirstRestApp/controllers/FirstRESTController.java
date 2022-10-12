package com.dmitrysulman.rest.FirstRestApp.controllers;

import com.dmitrysulman.rest.FirstRestApp.models.TestResponse;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FirstRESTController {

    @GetMapping("/sayHello")
    public String sayHello() {
        return "Hello world!";
    }

    @GetMapping("/consumer")
    public TestResponse consumer() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> jsonToSend = new HashMap<>();
        jsonToSend.put("name", "Dima");
        jsonToSend.put("job", "IT");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonToSend);

        String url1 = "https://reqres.in/api/users/2";
        String url2 = "https://reqres.in/api/users";
        TestResponse response1 = restTemplate.getForObject(url1, TestResponse.class);
        String response2 = restTemplate.postForObject(url2, request, String.class);

        System.out.println(response1.getData().getEmail());
        System.out.println(response2);
        return response1;
    }
}
