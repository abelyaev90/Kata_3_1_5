package org.example;

import org.example.entity.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.stream.Collectors;


public class Response {
    List<String> cookies = null;
    static final String baseUrl = "http://94.198.50.185:7081/api/users";
    static final  RestTemplate restTemplate = new RestTemplate();
    User sysUser = new User();

    ResponseEntity<String> getAll() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl, //
                HttpMethod.GET, entity, String.class);

        String result = response.getBody();

        System.out.println(result);
        System.out.println(response);
        System.out.println(response.getHeaders().get("Set-Cookie"));
        cookies = response.getHeaders().get("Set-Cookie");

        return response;
    }

    public void addUser() {

        sysUser.setName("James");
        sysUser.setLastName("Brown");
        sysUser.setAge((byte) 30);
        sysUser.setId(3L);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie",cookies.stream().collect(Collectors.joining(";")));
        HttpEntity<User> requestBody = new HttpEntity<>(sysUser, headers);

        ResponseEntity<String> responseAdd =  restTemplate.exchange("http://94.198.50.185:7081/api/users", HttpMethod.POST, requestBody, String.class);
        System.out.println(responseAdd.getBody());
        System.out.println(cookies);
    }

    public void editUser() {

        sysUser.setName("Thomas");
        sysUser.setLastName("Shelby");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie",cookies.stream().collect(Collectors.joining(";")));
        HttpEntity<User> requestBody = new HttpEntity<>(sysUser,headers);

        ResponseEntity<String> responseAdd =  restTemplate.exchange("http://94.198.50.185:7081/api/users", HttpMethod.PUT, requestBody, String.class);
        System.out.println(responseAdd.getBody());
        System.out.println(cookies);
    }

    public void delUser(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie",cookies.stream().collect(Collectors.joining(";")));
        HttpEntity<User> requestBody = new HttpEntity<>(sysUser,headers);

        ResponseEntity<String> responseAdd =  restTemplate.exchange("http://94.198.50.185:7081/api/users/3", HttpMethod.DELETE, requestBody, String.class);
        System.out.println(responseAdd.getBody());
        System.out.println(cookies);
    }
}
