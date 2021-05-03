package hishab.co.games.dice.service;

import hishab.co.games.dice.model.GameRoom;

import java.util.List;

public interface RoomService {

    /**
     * Adds a new game gameRoom
     *
     * @param gameRoom GameRoom to be added
     * @return Newly added gameRoom
     */
    GameRoom addRoom(GameRoom gameRoom);

    /**
     * Collects and returns a list of rooms
     *
     * @return Lis of rooms
     */
    List<GameRoom> getAllRooms();

    /**
     * Finds a room by id and returns if found
     *
     * @param id Id of the room
     * @return A room with given if found, null otherwise
     */
    GameRoom getRoomById(String id);

}
