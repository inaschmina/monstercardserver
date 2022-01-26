package battle;
import java.util.Objects;

public class Battle {
    BattleHandler battleHandler = new BattleHandler();
    BattleLogicHandler logicHandler = new BattleLogicHandler();

    public String battle(String username) {
        battleHandler.updateGameStatus(username, true);
        //sleep
        String opponent = battleHandler.ckeckOpponent();
        if(Objects.equals(opponent, "")) return "{\"code\": \"400\", \"message\": \"no opponent found\"}";
        battleHandler.updateGameStatus(username, false);
        battleHandler.updateGameStatus(opponent, false);
        logicHandler.battleLogic(username, opponent);
        return "{\"code\": \"200\", \"message\": \"" + BattleLogger.logger.getLog() + "\"}";
    }


}
