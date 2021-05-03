package hishab.co.games.dice.service;

import hishab.co.games.dice.config.GameConfig;
import hishab.co.games.dice.model.GameRoom;
import hishab.co.games.dice.model.Player;
import hishab.co.games.dice.model.RollingResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class GameServiceTest {

    @Autowired
    private GameService gameService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private GameConfig gameConfig;

    @Test
    void testDiceRollForSingleUser() {
        GameRoom room = this.roomService.addRoom(new GameRoom("Test"));
        Player player = new Player("test player", room.getId());
        this.gameService.join(player);
        RollingResult result = this.gameService.roll(player);
        assertNotNull(result, "Rolling result was null");
        assertEquals(result.getCurrentPlayer(), result.getNextPlayer(), "Both players were not same player");
    }

    @Test
    void testDiceRollForTwoUsers() {
        GameRoom room = this.roomService.addRoom(new GameRoom("Test"));
        Player player1 = new Player("test player-1", room.getId());
        this.gameService.join(player1);

        Player player2 = new Player("test player-2", room.getId());
        this.gameService.join(player2);


        RollingResult result = this.gameService.roll(player1);
        assertNotNull(result, "Rolling result was null");
        if (result.getCurrentPlayer().getCurrentScore() != gameConfig.startScore) {
            assertEquals(result.getNextPlayer(), player2, "Next turn should be for player 2");
        } else {
            assertEquals(result.getNextPlayer(), player1, "Next turn should be for player 1 as it rolls to the start");
        }
    }

    @Test
    void test_Next_Turn_For_The_Same_Player_When_Score_Is_Start_Score() {

        GameRoom room = this.roomService.addRoom(new GameRoom("Test"));
        Player player1 = new Player("test player-1", room.getId());
        this.gameService.join(player1);

        Player player2 = new Player("test player-2", room.getId());
        this.gameService.join(player2);

        RollingResult result = this.gameService.roll(player1);
        while (true) {
            if (result.getCurrentPlayer().getCurrentScore() == gameConfig.startScore) {
                assertEquals(result.getNextPlayer(), result.getCurrentPlayer(),
                        "Current and next were not the same at start score");
                break;
            }
            result = this.gameService.roll(result.getNextPlayer());
        }
    }

    @Test
    void test_Winning_Score_When_Result_Contains_A_Winner() {

        GameRoom room = this.roomService.addRoom(new GameRoom("Test"));
        Player player1 = new Player("test player-1", room.getId());
        this.gameService.join(player1);

        Player player2 = new Player("test player-2", room.getId());
        this.gameService.join(player2);

        RollingResult result = this.gameService.roll(player1);
        while (true) {
            if (result.isWinner()) {
                assertTrue(result.getCurrentPlayer().getTotalScore() >= gameConfig.winningScore,
                        "When result contains a winner current players scroe was not winning score");
                break;
            }
            result = this.gameService.roll(result.getNextPlayer());
        }
    }
}