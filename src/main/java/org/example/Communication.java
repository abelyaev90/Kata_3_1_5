package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component
public class Communication {

    private final RestTemplate restTemplate;
    private final String URL = "http://94.198.50.185:7081/api/users";

    public Communication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public ResponseEntity<List<User>> getAllUsersEntity() {

        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<User>>() {});

        HttpHeaders headers = new HttpHeaders();;
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        List<String> Cookie = responseEntity.getHeaders().get("Set-Cookie");
        System.out.println(responseEntity);
        System.out.println(responseEntity.getHeaders().get("Set-Cookie"));

        return responseEntity;
    }

    public User getUser(Long id) {
        User user = restTemplate.getForObject(URL + "/" + id, User.class);

        return user;
    }

    public void saveUser(User user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Long id = user.getId();
        String jsonString = mapper.writeValueAsString(user);

        HttpHeaders headers = new HttpHeaders();


        // headers.set("Cookie",cookies.stream().collect(Collectors.joining(";")));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        restTemplate.exchange("http://url", HttpMethod.POST, entity, String.class);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, user, String.class);
        System.out.println("new user was added to DB");
        System.out.println(responseEntity.getBody());
    }

    public void editUser(User user) {
        Long id = user.getId();
        restTemplate.put(URL, user);
        System.out.println("user with ID " + id + " updated");
    }

    public void deleteUser(Long id) {
        restTemplate.delete(URL + "/" + id);
        System.out.println("user with ID " + id + " deleted");
    }
}



