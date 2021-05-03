package hishab.co.games.dice.service.impl;

import hishab.co.games.dice.component.RoomCache;
import hishab.co.games.dice.exception.ApiException;
import hishab.co.games.dice.model.*;
import hishab.co.games.dice.service.GameService;
import hishab.co.games.dice.service.ScorerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiceGameService implements GameService {

    private ScorerService scorerService;
    private RoomCache cache;

    public DiceGameService(ScorerService scorerService,
                           RoomCache cache) {
        this.scorerService = scorerService;
        this.cache = cache;
    }

    /**
     * @inheritDoc
     */
    @Override
    public RollingResult roll(Player player) {
        Player existing = this.cache.getPlayer(player);
        if (existing == null) return null;
        this.cache.touchRoom(existing.getRoomId());
        RollingResult result = scorerService.rollAndSetScore(existing);
        result = existing.getRoom().afterDiceRolling(result);
        if (result != null) {
            if (result.isWinner()) {
                GameRoom room = this.cache.getRoomById(existing.getRoomId());
                room.reset();
            }
        }
        return result;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Player> quit(Player player) {
        return this.cache.removePlayer(player);
    }

    /**
     * @inheritDoc
     */
    @Override
    public GameJoinResult join(Player player) {
        GameRoom room = this.cache.getRoomById(player.getRoomId());
        if (room == null) {
            return new GameJoinResult(false, "No such room", new ArrayList<>());
        } else {
            if (room.hasAccess()) {
                player.setRoomId(room.getId());
                player.setRoom(room);
                Player lastPlayer = room.lastPlayer();
                try {
                    room.addPlayer(player);
                } catch (ApiException e) {
                    return new GameJoinResult(false, "Unable add new player", room.getAllPlayers());
                }
                int playersCount = room.playerCount();
                if (lastPlayer != null) {
                    player.setPosition(lastPlayer.getPosition().next(playersCount));
                } else {
                    player.setPosition(PlayerPosition.NORTH);
                }
                this.cache.touchRoom(room.getId());
                return new GameJoinResult(true, null, room.getAllPlayers());
            } else {
                return new GameJoinResult(false, "Access denied", room.getAllPlayers());
            }
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public RollingResult start(String roomId) {
        GameRoom room = this.cache.getRoomById(roomId);
        if (room == null) {
            return new RollingResult(new Exception(String.format("Room with id %s not found", roomId)));
        }
        return this.cache.resetGame(room);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void end(String roomId) {
        GameRoom room = this.cache.getRoomById(roomId);
        this.cache.removeRoom(room);
    }

}
