package hishab.co.games.dice.service.impl;

import hishab.co.games.dice.component.RoomCache;
import hishab.co.games.dice.model.GameRoom;
import hishab.co.games.dice.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameRoomService implements RoomService {

    // fields
    private RoomCache cache;

    // constructor
    public GameRoomService(RoomCache roomCache) {
        this.cache = roomCache;
    }

    /**
     * @inheritDoc
     */
    @Override
    public GameRoom addRoom(GameRoom gameRoom) {
        return this.cache.addRoom(gameRoom);
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<GameRoom> getAllRooms() {
        return this.cache.getAllRooms();
    }

    /**
     * @inheritDoc
     */
    @Override
    public GameRoom getRoomById(String id) {
        return this.cache.getRoomById(id);
    }

}
