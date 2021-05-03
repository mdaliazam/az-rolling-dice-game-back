package hishab.co.games.dice.controller;

import hishab.co.games.dice.component.RoomCache;
import hishab.co.games.dice.model.ApiResult;
import hishab.co.games.dice.model.ApiResultFactory;
import hishab.co.games.dice.model.GameRoom;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private RoomCache roomCache;

    public RoomController(RoomCache cache) {
        this.roomCache = cache;
    }

    @PostMapping()
    public ApiResult<GameRoom> addRoom(@RequestBody GameRoom room) {

        if (room.playerCount() == 0) {
            return new ApiResultFactory<GameRoom>(false)
                    .message("A new room must be opened by a player")
                    .create();
        }
        room.init();
        this.roomCache.addRoom(room);
        return new ApiResultFactory<GameRoom>(true)
                .message("A room has been added successfully")
                .entity(room)
                .create();
    }

    @GetMapping
    public ApiResult<GameRoom> getAllRooms() {
        List<GameRoom> rooms = this.roomCache.getAllRooms();
        return new ApiResultFactory<GameRoom>(true)
                .message(String.format("Total %s room(s) found", rooms.size()))
                .entities(rooms)
                .create();
    }

}
