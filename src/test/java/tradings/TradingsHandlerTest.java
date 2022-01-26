package tradings;

import card.CardHandler;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TradingsHandlerTest {
    TradingsHandler tradingsHandler = new TradingsHandler();
    CardHandler cardHandler = new CardHandler();

    @Test
    void createTradingDeal() throws IOException {
        String carddata = "{\"Id\":\"testCardId1\", \"Name\":\"WaterGoblin\", \"Damage\": 20.0}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(carddata);
        cardHandler.createCard(node, -1, "testUser1", false);
        String deal = "{\"Id\": \"testTradeId1\", \"CardToTrade\": \"testCardId1\", \"Type\": \"monster\", \"MinimumDamage\": 15}";
        node = mapper.readTree(deal);
        assertEquals("{\"code\": \"200\", \"message\": \"trade created\"}", tradingsHandler.createTradingDeal(node));
    }

    @Test
    void checkTradingDeals() {
        assertNotEquals("{\"code\": \"404\", \"message\": \"select of tradings failed\"}", tradingsHandler.checkTradingDeals());
    }

    @Test
    void tradeOwnCard() {
        assertEquals("{\"code\": \"400\", \"message\": \"card already belongs to this user\"}", tradingsHandler.trade(null, "testTradeId1", "testUser1"));
    }

    @Test
    void deleteTradeOffer() {
        assertNotEquals("{\"code\": \"400\", \"message\": \"delete of trading failed\"}", tradingsHandler.deleteTradeOffer("testTradeId1"));
    }

}