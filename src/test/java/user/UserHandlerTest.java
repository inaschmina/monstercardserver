package user;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UserHandlerTest {
    UserHandler userHandler = new UserHandler();

    @Test
    void createUser() throws IOException {
        String data = "{\"Username\":\"testUser1\", \"Password\":\"testPsw1\"}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(data);
        assertEquals("{\"code\": \"200\", \"message\": \"user created\"}", userHandler.createUser(node));
    }

    @Test
    void getUserData() {
        assertNotEquals("{\"code\": \"404\", \"message\": \"user not found\"}", userHandler.getUserData("testUser1"));
    }

    @Test
    void loginUser() throws IOException {
        String data = "{\"Username\":\"testUser1\", \"Password\":\"testPsw1\"}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(data);
        assertEquals("{\"code\": \"200\", \"message\": \"true \"}", userHandler.loginUser(node));
    }
}