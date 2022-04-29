import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RestClient {

    private static final String GET_USER_ENDPOINT_URL = "http://94.198.50.185:7081/api/users";

    static RestClient restClient = new RestClient();
    static RestTemplate restTemplate = new RestTemplate();
    static List<String> cookies;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        restClient.getUsers();
        restClient.createUser();
        restClient.putUser();
        restClient.deleteUser();
    }

    private void getUsers() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity < String > entity = new HttpEntity< String >("parameters", headers);

        String result = restTemplate.exchange(GET_USER_ENDPOINT_URL, HttpMethod.GET, entity,
                String.class).getHeaders().get("Set-Cookie").get(0).substring(0,43);

        cookies = restTemplate.exchange(GET_USER_ENDPOINT_URL, HttpMethod.GET, entity,
                String.class).getHeaders().get("Set-Cookie");
        System.out.println(result);
    }

    private void createUser() {

        HttpHeaders headers1 = new HttpHeaders();
        headers1.setContentType(MediaType.APPLICATION_JSON);
        headers1.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers1.set("Cookie", cookies.stream().collect(Collectors.joining(";")));
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("id", 3);
        personJsonObject.put("name", "James");
        personJsonObject.put("lastName", "Brown");
        personJsonObject.put("age", 50);
        HttpEntity < String > entity = new HttpEntity<String>(personJsonObject.toString(), headers1);
        System.out.println("creating...");



        ResponseEntity < String > result = restTemplate.exchange(GET_USER_ENDPOINT_URL, HttpMethod.POST, entity,
                String.class);


        System.out.println(result);
    }

    private void putUser() {

        HttpHeaders headers2 = new HttpHeaders();
        headers2.setContentType(MediaType.APPLICATION_JSON);
        headers2.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers2.set("Cookie", cookies.stream().collect(Collectors.joining(";")));
//        headers2.set("method", "put");
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("id", 3);
        personJsonObject.put("name", "Thomas");
        personJsonObject.put("lastName", "Shelby");
        personJsonObject.put("age", 50);
        HttpEntity < String > entity = new HttpEntity<String>(personJsonObject.toString(), headers2);
        System.out.println("updating...");



        ResponseEntity < String > result = restTemplate.exchange(GET_USER_ENDPOINT_URL, HttpMethod.PUT, entity,
                String.class);


        System.out.println(result);
    }

    private void deleteUser() {

        HttpHeaders headers3 = new HttpHeaders();
        headers3.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers3.set("Cookie", cookies.stream().collect(Collectors.joining(";")));
        HttpEntity < String > entity = new HttpEntity< String >(headers3);

        ResponseEntity<String> result = restTemplate.exchange(GET_USER_ENDPOINT_URL + "/3", HttpMethod.DELETE, entity,
                String.class);
        System.out.println(result);
//        cookies = restTemplate.exchange(GET_USER_ENDPOINT_URL, HttpMethod.GET, entity,
//                String.class).getHeaders().get("Set-Cookie");






    }

}
