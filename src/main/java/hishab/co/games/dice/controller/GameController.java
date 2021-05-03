package hishab.co.games.dice.controller;

import hishab.co.games.dice.model.ApiResult;
import hishab.co.games.dice.model.ApiResultFactory;
import hishab.co.games.dice.model.Player;
import hishab.co.games.dice.model.RollingResult;
import hishab.co.games.dice.service.GameService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private GameService gameService;

    public GameController(GameService service) {
        this.gameService = service;
    }

    @PostMapping("/roll")
    public ApiResult<RollingResult> roll(@RequestBody Player player) {

        RollingResult result = this.gameService.roll(player);
        if (result == null) {
            return new ApiResultFactory<RollingResult>(false)
                    .message("Roller service error, please try again")
                    .create();
        }
        return new ApiResultFactory<RollingResult>(true)
                .entity(result)
                .create();
    }

    @GetMapping("/start/{roomId}")
    public ApiResult<RollingResult> startGame(@PathVariable String roomId) {
        RollingResult result = this.gameService.start(roomId);
        return new ApiResultFactory<RollingResult>(result != null)
                .entity(result)
                .create();
    }

    @GetMapping("/end/{roomId}")
    public ApiResult endGame(@PathVariable String roomId) {
        this.gameService.end(roomId);
        return new ApiResultFactory(true)
                .message("Game was ended and room was removed")
                .create();
    }

}
