package tradings;

import card.CardHandler;
import db.TradingsDBHandler;
import org.codehaus.jackson.JsonNode;

import java.util.Objects;

public class TradingsHandler {
    TradingsDBHandler db = new TradingsDBHandler();


    public String checkTradingDeals() {
        return db.selectAllTradings();
    }

    public String createTradingDeal(JsonNode credentials) {
        CardHandler cardHandler = new CardHandler();
        cardHandler.lockOrUnlockCard(credentials.get("CardToTrade").getTextValue(), true);
        return db.insertTrade(credentials);
    }

    public String deleteTradeOffer(String tradingId) {
        CardHandler cardHandler = new CardHandler();
        String cardId = db.selectCardToTradeId(tradingId);
        cardHandler.lockOrUnlockCard(cardId, false);
        return db.deleteTrade(tradingId);
    }

    public String trade(JsonNode credentials, String tradingID, String user) {
        CardHandler cardHandler = new CardHandler();
        String cardId = db.selectCardToTradeId(tradingID);
        String cardOwner = cardHandler.getOwnerFromCard(cardId);
        if(Objects.equals(user, cardOwner)) return "{\"code\": \"400\", \"message\": \"card already belongs to this user\"}";
        cardHandler.updateOwner(cardId, user);
        String returnString = cardHandler.updateOwner(credentials.getTextValue(), cardOwner);
        cardHandler.lockOrUnlockCard(cardId, false);
        cardHandler.lockOrUnlockCard(credentials.getTextValue(), false);
        db.deleteTrade(tradingID);
        return returnString;
    }

}
