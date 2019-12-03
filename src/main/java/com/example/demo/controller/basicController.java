package com.example.demo.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/caller")
public class basicController {

	@Value("${client.demo.endpoint}")
	private String DemoEndpoint;
	
	private static RestTemplate restTemplate;
	
	@RequestMapping("/getDemo")
	public String init() throws UnknownHostException {
		String host = InetAddress.getLocalHost().getHostName();
		String host_ip = InetAddress.getLocalHost().getHostAddress();
		
		HttpHeaders headers = new HttpHeaders();
        //headers.add("Authentication", key);


        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        
        //순서대로 url, method, entity(header, params), return type
        String call_uri = DemoEndpoint + "/getInfo";
        ResponseEntity<String> result = restTemplate.exchange(
        		call_uri, HttpMethod.GET, httpEntity, String.class, params);
		
        String Code = result.getStatusCode().toString();
        
		return "getDemo(" + Code +") => "+ result.toString();
		
	}
	
}
