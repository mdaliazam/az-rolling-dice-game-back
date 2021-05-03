package hishab.co.games.dice.model;

import hishab.co.games.dice.exception.ApiException;
import hishab.co.games.dice.exception.RoomFullException;
import hishab.co.games.dice.exception.RoomLockedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameRoomTest {

    @Test
    public void testCanWhenMaxNotReached() throws ApiException {
        GameRoom room = new GameRoom();
        Player player1 = new Player("Mohammad");
        Player player2 = new Player("Ali");
        Player player3 = new Player("Azam");

        room.addPlayer(player1);
        room.addPlayer(player2);
        room.addPlayer(player3);

        assertTrue(room.hasAccess(), "Test failed: a room was unable to add more player");
    }

    @Test
    public void testCanAddOverMaxPlayers() throws ApiException {
        GameRoom room = new GameRoom();
        Player player1 = new Player("Mohammad");
        Player player2 = new Player("Ali");
        Player player3 = new Player("Azam");
        Player player4 = new Player("James");

        room.addPlayer(player1);
        room.addPlayer(player2);
        room.addPlayer(player3);
        room.addPlayer(player4);

        assertFalse(room.hasAccess(), "Test failed: a room can add more than 4 players");
    }

    @Test
    public void testCanNotAddPlayerWhenRoomIsLocked() throws ApiException {
        GameRoom room = new GameRoom();
        Player player1 = new Player("Mohammad");
        Player player2 = new Player("Ali");
        Player player3 = new Player("Azam");

        room.addPlayer(player1);
        room.addPlayer(player2);
        room.addPlayer(player3);
        room.setLocked(true);

        assertFalse(room.hasAccess(), "Test failed: a room can add player even when room is locked");
    }

    @Test
    public void testCanNotAddPlayerWhenGameStarted() throws ApiException {
        GameRoom room = new GameRoom();
        Player player1 = new Player("Mohammad");
        Player player2 = new Player("Ali");
        Player player3 = new Player("Azam");

        room.addPlayer(player1);
        room.addPlayer(player2);
        room.addPlayer(player3);
        room.start();
        assertFalse(room.hasAccess(), "Test failed: a room can add player even when a game was started");
    }

    @Test
    public void testCanAddPlayerWhenGameIsOver() throws ApiException {
        GameRoom room = new GameRoom();
        Player player1 = new Player("Mohammad");
        Player player2 = new Player("Ali");
        Player player3 = new Player("Azam");
        Player player4 = new Player("James");

        room.addPlayer(player1);
        room.addPlayer(player2);
        room.addPlayer(player3);
        room.start();
        assertFalse(room.hasAccess(), "Test failed: a room can add player even when a game was started");

        room.gameOver();
        assertTrue(room.hasAccess(), "Test failed: a room was unable to add more player even the game is over");
        room.addPlayer(player4);
    }

    @Test
    public void expectRoomLockedException() {
        assertThrows(
                RoomLockedException.class,
                () -> {
                    GameRoom room = new GameRoom();
                    room.setLocked(true);
                    room.addPlayer(new Player("Mohammad"));
                },
                "Expected it threw room locked exception, but it didn't"
        );
    }

    @Test
    public void expectRoomFullException() {
        assertThrows(
                RoomFullException.class,
                () -> {
                    GameRoom room = new GameRoom();
                    room.addPlayer(new Player("Mohammad"));
                    room.addPlayer(new Player("Ali"));
                    room.addPlayer(new Player("Azam"));
                    room.addPlayer(new Player("Mitchael"));
                    room.addPlayer(new Player("Almir"));
                },
                "Expected it threw room full exception, but it didn't"
        );
    }
}
