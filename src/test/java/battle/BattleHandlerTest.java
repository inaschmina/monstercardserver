package battle;

import card.Card;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class BattleHandlerTest {
    BattleHandler battleHandler = new BattleHandler();
    private Object ArrayList;

    @Test
    void getDeckArrayNUll() {
        ArrayList<Card> array = new ArrayList<Card>();
        assertArrayEquals(array.toArray(), battleHandler.getDeckArray("testUser1").toArray());
    }

}