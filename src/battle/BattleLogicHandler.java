package battle;

import card.Card;
import user.UserHandler;

import java.util.ArrayList;
import java.util.Objects;

public class BattleLogicHandler {
    BattleHandler battleHandler = new BattleHandler();
    UserHandler userHandler = new UserHandler();
    BattleLogger logger = BattleLogger.getInstance();

    public void battleLogic(String username, String opponentName) {
        ArrayList<Card> userCards = battleHandler.getDeckArray(username);
        ArrayList<Card> opponentCards = battleHandler.getDeckArray(opponentName);
        logger.log("Both card decks have been created");
        int userWon = 0;
        int opponentWon = 0;
        int userLost = 0;
        int opponentLost = 0;
        int userELO = userHandler.getELO(username);
        int opponentELO = userHandler.getELO(opponentName);
        for (int rounds = 0; rounds < 100; rounds++) {
            //draw random card
            Card userCard = userCards.get((int) (Math.random() * userCards.size()));
            Card opponentCard = opponentCards.get((int) (Math.random() * opponentCards.size()));
            logger.log("Both players have drawn their cards");
            logger.log(userCard.getName() + " vs. " + opponentCard.getName());
            Card loser = checkSpeciallity(userCard, opponentCard);
            //if looser isn't clear because of speciality actual battle is fought
            if(loser == null) loser = battleCards(userCard, opponentCard);
            //if loser still null battle was a draw if ot there is a winner and a looser
            if (loser != null) {
                if (loser == userCard) {
                    //user lost -> opponent gets the card
                    logger.log(username + " lost");
                    userCards.remove(userCard);
                    opponentCards.add(userCard);
                    userLost++;
                    opponentWon++;
                    userELO = calculateELO(userELO, opponentELO, 0);
                    opponentELO = calculateELO(opponentELO, userELO, 1);
                } else if (loser == opponentCard) {
                    logger.log(opponentName + " lost");
                    opponentCards.remove(opponentCard);
                    userCards.add(opponentCard);
                    opponentLost++;
                    userWon++;
                    opponentELO = calculateELO(opponentELO, userELO, 0);
                    userELO = calculateELO(userELO, userELO, 1);
                }
            }
            if (loser == null) logger.log("This round was a draw.");
            //if round was draw
            opponentELO = calculateELO(opponentELO, userELO, 0.5);
            userELO = calculateELO(userELO, userELO, 0.5);
            if (userCards.isEmpty() || opponentCards.isEmpty()) break;
        }
        battleHandler.updateStats(username, userWon, userLost, userELO);
        battleHandler.updateStats(opponentName, opponentWon, opponentLost, opponentELO);
        logger.log("Stats have been updated");
    }

    private int calculateELO(int elo1, int elo2, double result) {
        int k = 40;
        int Ea;
        if(elo1 >= 2400 && elo2 >= 2400){
            k = 10;
        }
        Ea = 1/(1 + k ^((elo2 - elo1) / 400));

        return (int) (elo1 + k *(result - Ea));
    }

    public Card battleCards(Card user, Card opponent) {
        //only if spell is involved elements matter

        if(user.getName().toLowerCase().contains("spell") || opponent.getName().toLowerCase().contains("spell")) {
            if(Objects.equals(user.getElement(), "fire") && Objects.equals(opponent.getElement(), "water")) {
                logger.log("water beat fire");
                user.setDamage(user.getDamage()/2);
                opponent.setDamage(opponent.getDamage()*2);
            }
            else if (Objects.equals(opponent.getElement(), "fire") && Objects.equals(user.getElement(), "water")) {
                logger.log("water beat fire");
                opponent.setDamage(user.getDamage()/2);
                user.setDamage(user.getDamage() * 2);
            }
            else if (Objects.equals(opponent.getElement(), "normal") && Objects.equals(user.getElement(), "fire")) {
                logger.log("fire beat normal");
                user.setDamage(user.getDamage()/2);
                opponent.setDamage(user.getDamage() * 2);
            }
            else if (Objects.equals(opponent.getElement(), "normal") && Objects.equals(user.getElement(), "fire")) {
                logger.log("fire beat normal");
                opponent.setDamage(user.getDamage()/2);
                user.setDamage(user.getDamage() * 2);
            }
            else if (Objects.equals(opponent.getElement(), "water") && Objects.equals(user.getElement(), "normal")) {
                logger.log("normal beat water");
                user.setDamage(user.getDamage()/2);
                opponent.setDamage(user.getDamage() * 2);
            }
            else if (Objects.equals(opponent.getElement(), "water") && Objects.equals(user.getElement(), "normal")) {
                logger.log("normal beat water");
                opponent.setDamage(user.getDamage()/2);
                user.setDamage(user.getDamage() * 2);
            }
        }
        //return card that looses
        if(user.getDamage() > opponent.getDamage()) return opponent;
        if(opponent.getDamage() > user.getDamage()) return user;
        return null;
    }

    public Card checkSpeciallity(Card user, Card opponent) {
        String userName = user.getName();
        String opponentName = opponent.getName();
        if(userName.contains("Goblin") && opponentName.contains("Dragon")) {
            logger.log("Goblins are too afraid of Dragons to attack");
            return user;
        }
        if(opponentName.contains("Goblin") && userName.contains("Dragon")) {
            logger.log("Goblins are too afraid of Dragons to attack");
            return opponent;
        }
        if(userName.contains("Ork") && opponentName.contains("Wizard")) {
            logger.log("Wizzard can control Orks so they are not able to damage them");
            return user;
        }
        if(opponentName.contains("Ork") && userName.contains("Wizard")) {
            logger.log("Wizzard can control Orks so they are not able to damage them");
            return opponent;
        }
        if(userName.contains("Knight") && opponentName.contains("WaterSpell")) {
            logger.log("The armor of Knights is so heavy that WaterSpells make them drown them instantly. ");
            return user;
        }
        if(opponentName.contains("Knight") && userName.contains("WaterSpell")) {
            logger.log("The armor of Knights is so heavy that WaterSpells make them drown them instantly. ");
            return opponent;
        }
        if(userName.contains("Spell") && opponentName.contains("Kraken")) {
            logger.log("The Kraken is immune against spells.");
            return user;
        }
        if(opponentName.contains("Spell") && userName.contains("Kraken")) {
            logger.log("The Kraken is immune against spells.");
            return opponent;
        }
        if(userName.contains("Dragon") && opponentName.contains("FireElv")) {
            logger.log("The FireElves know Dragons since they were little and can evade their attacks. ");
            return user;
        }
        if(opponentName.contains("Dragon") && userName.contains("FireElv")) {
            logger.log("The FireElves know Dragons since they were little and can evade their attacks. ");
            return opponent;
        }
        else return null;

    }

}
