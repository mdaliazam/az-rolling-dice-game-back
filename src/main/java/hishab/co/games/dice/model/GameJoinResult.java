package hishab.co.games.dice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GameJoinResult {

    private boolean joined;
    private String message;
    private List<Player> players = new ArrayList<>();

    public GameJoinResult(boolean joined, String message, List<Player> players) {
        this.joined = joined;
        this.message = message;
        this.players = players;
    }
}
