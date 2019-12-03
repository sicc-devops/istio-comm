package com.example.demo.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/caller")
public class basicController {

	@Value("${client.demo.endpoint}")
	private String DemoEndpoint;
	
	@Autowired
	private RestTemplate restTemplate;

	private Logger logger = LoggerFactory.getLogger(basicController.class);
		
	@RequestMapping("/getDemo")
	public String init() throws UnknownHostException {
		String host = InetAddress.getLocalHost().getHostName();
		String host_ip = InetAddress.getLocalHost().getHostAddress();
		
		HttpHeaders headers = new HttpHeaders();
		
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        
        //순서대로 url, method, entity(header, params), return type
        String call_uri = DemoEndpoint + "/getInfo";
        //String call_uri = "http://35.193.168.90/demo/getInfo";
        logger.info("log>>>>>>>>>>>>>>>  /getDemo called");
        
        //ResponseEntity<String> result = restTemplate.exchange(
        //		call_uri, HttpMethod.GET, httpEntity, String.class);
		
        String result = restTemplate.getForObject( call_uri, String.class);

            	
        //String Code = result.getStatusCode().toString();
        
		//return "getDemo(" + Code +") => "+ result.getBody();
        return result;

	}
	
}
