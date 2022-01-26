package battle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BattleLoggerTest {

    @Test
    void checkLoggerNotNull() {
        BattleLogger logger = BattleLogger.getInstance();
        logger.log("This is a message that should be appended");
        assertNotEquals(null, logger.getLog());
    }

}