package battle;

import card.Card;
import card.Monstercard;
import card.Spellcard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BattleLogicHandlerTest {
    BattleLogicHandler logicHandler = new BattleLogicHandler();


    @Test
    void battleCardsWithSpell() {
        Card card1 = new Monstercard("1", "WaterGoblin", 10.0, "water");
        Card card2 = new Spellcard("2", "FireSpell", 10.0, "fire");
        assertEquals(card2, logicHandler.battleCards(card1, card2));
    }

    @Test
    void battleCardsOnlyMonstercards() {
        Card card1 = new Monstercard("1", "WaterGoblin", 10.0, "water");
        Card card2 = new Spellcard("2", "FireGoblin", 20.0, "fire");
        assertEquals(card1, logicHandler.battleCards(card1, card2));
    }

    @Test
    void battleCardsOnlyMonstercardsDraw() {
        Card card1 = new Monstercard("1", "WaterGoblin", 10.0, "water");
        Card card2 = new Spellcard("2", "FireGoblin", 10.0, "fire");
        assertNull(logicHandler.battleCards(card1, card2));
    }

    @Test
    void checkSpeciallityWithMatch() {
        Card card1 = new Monstercard("1", "Dragon", 10.0, "regular");
        Card card2 = new Spellcard("2", "FireElv", 5.0, "fire");
        assertEquals(card1, logicHandler.checkSpeciallity(card1, card2));
    }

    @Test
    void checkSpeciallityWithoutMatch() {
        Card card1 = new Monstercard("1", "Kraken", 10.0, "regular");
        Card card2 = new Spellcard("2", "FireElv", 20.0, "fire");
        assertNull(logicHandler.checkSpeciallity(card1, card2));
    }
}