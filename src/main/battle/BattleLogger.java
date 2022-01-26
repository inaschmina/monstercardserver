package battle;

public class BattleLogger {

    public static BattleLogger logger = null;
    StringBuilder battleLog = new StringBuilder();

    private BattleLogger() {};

    public static BattleLogger getInstance() {
        if(logger == null) logger = new BattleLogger();
        return logger;
    }

    public void log(String message) {
        this.battleLog.append(message).append("\r\n");
    }

    public String getLog() {
        return this.battleLog.toString();
    }

}
