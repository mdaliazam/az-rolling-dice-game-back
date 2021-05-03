package hishab.co.games.dice.component;


import hishab.co.games.dice.model.Cached;
import hishab.co.games.dice.model.GameRoom;
import hishab.co.games.dice.model.Player;
import hishab.co.games.dice.model.RollingResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class RoomCache {

    @Value("${hishab.co.dice.game.room.maxIdle:#{90}}")
    private int maxIdleTime;

    private final Map<String, Cached<GameRoom>> rooms = new ConcurrentHashMap<>();

    @Scheduled(cron = "${hishab.co.dice.game.room-cleanup-cron:#{'* */10 * * * *'}}")
    public void removeExpiredRoom() {
        System.out.println("Rooms removing at " + LocalDateTime.now());
        try {
            final List<String> roomIdListToRemove = new ArrayList<>();
            synchronized (this.rooms) {
                for (Cached<GameRoom> cached : this.rooms.values()) {
                    if (cached.expires(this.maxIdleTime)) {
                        roomIdListToRemove.add(cached.get().getId());
                    }
                }

                for (String roomId : roomIdListToRemove) {
                    this.rooms.remove(roomId);
                }
            }
        } catch (Exception ex) {
            // TODO log here
        }
    }

    public GameRoom addRoom(GameRoom gameRoom) {
        if (gameRoom.getId() == null) {
            gameRoom.setId(UUID.randomUUID().toString());
        }
        Cached<GameRoom> existing = rooms.get(gameRoom.getId());
        if (existing != null) {
            return existing.get();
        }
        rooms.put(gameRoom.getId(), new Cached<>(gameRoom));
        return gameRoom;
    }

    public List<GameRoom> getAllRooms() {
        return rooms.values()
                .stream().filter(cachedRoom -> !cachedRoom.expires(this.maxIdleTime))
                .map(cachedRoom -> cachedRoom.get())
                .collect(Collectors.toList());
    }

    public GameRoom getRoomById(String id) {
        for (Cached<GameRoom> cachedRoom : this.rooms.values()) {
            if (cachedRoom.get().getId().equals(id)) {
                return cachedRoom.get();
            }
        }
        return null;
    }

    public List<Player> removePlayer(Player player) {
        GameRoom room = this.getRoomById(player.getRoomId());
        if (room != null) {
            room.removePlayer(player);
            return room.getAllPlayers();
        }
        return new ArrayList<>();
    }

    public RollingResult resetGame(GameRoom room) {
        Cached<GameRoom> cached = this.rooms.get(room.getId());
        if (cached == null || cached.expires(this.maxIdleTime)) {
            return null;
        }
        GameRoom gameRoom = cached.get();
        for (Player player : gameRoom.getAllPlayers()) {
            player.reset();
        }
        touchRoom(room.getId());
        return gameRoom.start();
    }

    public void removeRoom(GameRoom room) {
        this.rooms.remove(room.getId());
    }

    public Player getPlayer(Player player) {
        Cached<GameRoom> cached = this.rooms.get(player.getRoomId());
        if (cached != null) {
            GameRoom room = cached.get();
            int index = room.getAllPlayers().indexOf(player);
            if (index > -1) {
                return room.getAllPlayers().get(index);
            }
        }
        return null;
    }

    public void touchRoom(String roomId) {
        Cached<GameRoom> cached = this.rooms.get(roomId);
        if (cached != null) {
            cached.setLastActive(LocalDateTime.now());
        }
    }
}
