package hishab.co.games.dice.controller;

import hishab.co.games.dice.component.RoomCache;
import hishab.co.games.dice.model.ApiResult;
import hishab.co.games.dice.model.ApiResultFactory;
import hishab.co.games.dice.model.GameJoinResult;
import hishab.co.games.dice.model.Player;
import hishab.co.games.dice.service.GameService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private RoomCache roomCache;
    private GameService gameService;

    public PlayerController(RoomCache cache, GameService service) {
        this.roomCache = cache;
        this.gameService = service;
    }

    @PostMapping
    public ApiResult<GameJoinResult> join(@RequestBody Player player) {
        player.init();
        return new ApiResultFactory<GameJoinResult>(true)
                .entity(this.gameService.join(player))
                .create();
    }

}
