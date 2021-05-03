package hishab.co.games.dice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RollingResult {

    private boolean winner;
    private int total;
    private int currentValue;
    private Player nextPlayer;
    private Player currentPlayer;
    private Throwable error;

    public RollingResult(boolean winner, int total, int currentValue, Player currentPlayer, Player nextPlayer) {
        this.winner = winner;
        this.total = total;
        this.currentValue = currentValue;
        this.currentPlayer = currentPlayer;
        this.nextPlayer = nextPlayer;
    }

    public RollingResult(Throwable error) {
        this.error = error;
    }

    public RollingResult(Player nextPlayer) {
        this.nextPlayer = nextPlayer;
    }
}
