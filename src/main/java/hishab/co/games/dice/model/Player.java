package hishab.co.games.dice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Player implements Cloneable {

    private String id;
    @JsonIgnore
    private GameRoom room;
    private String roomId;
    private String nickName;
    @JsonIgnore
    private boolean startRolling;
    private int totalScore;
    private int currentScore;
    private PlayerPosition position;

    // constructor that takes nick name
    public Player(String nickName) {
        this.nickName = nickName;
        this.id = UUID.randomUUID().toString();
    }

    public Player(String nickName, String roomId) {
        this.nickName = nickName;
        this.roomId = roomId;
        this.id = UUID.randomUUID().toString();
    }

    public Player(String id, GameRoom room, String roomId, String nickName,
                  boolean startRolling, int totalScore, int currentScore,
                  PlayerPosition position) {
        this.id = id;
        this.room = room;
        this.roomId = roomId;
        this.nickName = nickName;
        this.startRolling = startRolling;
        this.totalScore = totalScore;
        this.currentScore = currentScore;
        this.position = position;
    }

    public void reset() {
        this.startRolling = false;
        this.totalScore = 0;
    }

    public void init() {
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Player)) {
            return false;
        }
        return this.hashCode() == other.hashCode();
    }

    public Player clone() {
        return new Player(this.id, this.room, this.roomId, this.nickName,
                this.startRolling, this.totalScore, this.currentScore, this.position);
    }
}
