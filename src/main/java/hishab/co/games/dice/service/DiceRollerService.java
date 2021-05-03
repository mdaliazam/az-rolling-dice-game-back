package hishab.co.games.dice.service;

public interface DiceRollerService {

    /**
     * Rolls a dice and returns the number shown its head
     * Dice rolling service is an external service and implementation may
     * vary
     *
     * @return Integer that is shown on the dice head
     */
    Integer roll();
}
