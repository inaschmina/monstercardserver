package card;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CardHandlerTest {
    CardHandler cardHandler = new CardHandler();

    @Test
    void getOwnerFromCardBeforeChange() throws IOException {
        String carddata = "{\"Id\":\"testCardId2\", \"Name\":\"WaterGoblin\", \"Damage\": 20.0}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(carddata);
        cardHandler.createCard(node, -1, "testUser2", false);
        assertEquals("testUser2", cardHandler.getOwnerFromCard("testCardId2"));
    }

    @Test
    void updateOwner() {
        assertEquals("{\"code\": \"200\", \"message\": \"owner updated\"}", cardHandler.updateOwner("testCardId2", "testUserNEW"));
    }

    @Test
    void getOwnerFromCardAfterChange() {
        assertEquals("testUserNEW", cardHandler.getOwnerFromCard("testCardId2"));
    }
}