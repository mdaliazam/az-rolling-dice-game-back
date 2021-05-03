package hishab.co.games.dice.service;


import hishab.co.games.dice.model.GameJoinResult;
import hishab.co.games.dice.model.Player;
import hishab.co.games.dice.model.RollingResult;

import java.util.List;

public interface GameService {

    /**
     * Rolls the dice for the current player
     *
     * @param player The current player throwing the dice (auto throw)
     * @return Instance of rolling result
     */
    RollingResult roll(Player player);

    /**
     * A player quits from the game
     *
     * @param player The player wishes to quit
     */
    List<Player> quit(Player player);


    /**
     * A player wishes to join a game room
     *
     * @param player Player who wishes to join
     * @return An instance of game result
     */
    GameJoinResult join(Player player);

    /**
     * Starts a game room (remove)
     *
     * @param roomId Id of the room to be started
     */
    RollingResult start(String roomId);

    /**
     * Ends a game room (remove)
     *
     * @param roomId Id of the room to be ended
     */
    void end(String roomId);

}
