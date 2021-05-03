package hishab.co.games.dice.model;

import hishab.co.games.dice.exception.ApiException;
import hishab.co.games.dice.exception.RoomFullException;
import hishab.co.games.dice.exception.RoomLockedException;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class GameRoom {

    public GameRoom(String name) {
        this.name = name;
    }

    private String id;
    private String name;
    private LocalDateTime createdAt = LocalDateTime.now();
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.MODULE)
    private List<Player> players = new ArrayList<>();
    private boolean locked;
    private String nextTurn;
    private PlayerPosition startPosition = PlayerPosition.NORTH;
    private boolean startRolling = false;
    private boolean soloMode = false;

    public boolean hasAccess() {
        return (!locked && players.size() < 4);
    }

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final Object lock = new Object();

    public List<Player> addPlayer(Player player) throws ApiException {

        synchronized (this.lock) {

            if (this.locked) {
                throw new RoomLockedException("The room is locked for adding new player");
            }

            if (this.players.size() == 4) {
                throw new RoomFullException("The room is full and unable to add new player");
            }

            if (player.getId() == null) {
                player.setId(UUID.randomUUID().toString());
            }
            player.setRoomId(this.id);
            player.setRoom(this);
            this.players.add(player);

            return getAllPlayers();
        }
    }

    public List<Player> getAllPlayers() {
        synchronized (this.lock) {
//            return Collections.unmodifiableList(this.players);
            return new ArrayList<>(this.players);
        }
    }

    public void setAllPlayers(List<Player> players) {
        synchronized (this.lock) {
            this.players.addAll(players);
        }
    }

    public int playerCount() {
        synchronized (this.lock) {
            return this.players.size();
        }
    }

    public List<Player> removePlayer(Player player) {
        synchronized (this.lock) {
            this.players.remove(player);
            return this.getAllPlayers();
        }
    }

    public Player next(PlayerPosition position) {
        PlayerPosition nextPosition = position.next(this.players.size());
        for (Player player : this.players) {
            if (player.getPosition() == nextPosition) {
                return player;
            }
        }
        return null;
    }

    public void init() {
        this.id = UUID.randomUUID().toString();
        Player current = null;
        int playersCount = this.players.size();
        for (Player player : this.players) {
            player.setId(UUID.randomUUID().toString());
            if (current == null) {
                player.setPosition(PlayerPosition.NORTH);
            } else {
                player.setPosition(current.getPosition().next(playersCount));
            }
            player.setRoomId(this.id);
            player.setRoom(this);
            current = player;
        }
    }

    public void reset() {
        for (Player player : this.players) {
            player.setTotalScore(0);
            player.setCurrentScore(0);
            player.setStartRolling(false);
        }
        this.locked = false;
    }

    public Player lastPlayer() {
        if (this.players.isEmpty()) {
            return null;
        }
        return this.players.get(this.players.size() - 1);
    }

    public RollingResult afterDiceRolling(RollingResult result) {
        synchronized (this.lock) {
            if (result == null) {
                return null;
            }
            this.nextTurn = result.getNextPlayer().getId();
            return result;
        }
    }

    public RollingResult start() {
        synchronized (this.lock) {
            if (this.players.isEmpty()) {
                return null;
            }

            Player nextPlayer = null;
            if (!startRolling) {
                nextPlayer = getPlayerAtPosition(this.startPosition);
                startRolling = true;
            } else {
                this.startPosition = this.startPosition.next(this.players.size());
                nextPlayer = getPlayerAtPosition(this.startPosition);
            }

            this.locked = true;
            return new RollingResult(nextPlayer);
        }
    }

    public void gameOver() {
        synchronized (this.lock) {
            this.locked = false;
        }
    }

    // helpers
    private Player getPlayerAtPosition(PlayerPosition position) {
        for (Player player : this.players) {
            if (player.getPosition() == position) {
                return player;
            }
        }
        return null;
    }

}
