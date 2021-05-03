package hishab.co.games.dice.service;

import hishab.co.games.dice.model.Player;
import hishab.co.games.dice.model.RollingResult;

public interface ScorerService {

    /**
     * After rolling a dice, we get a score, this service
     * will set that score to the intended player
     *
     * @param player Player to whom score will be set
     * @return An instance of rolling result
     */
    RollingResult rollAndSetScore(Player player);
}
