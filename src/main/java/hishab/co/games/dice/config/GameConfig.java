package hishab.co.games.dice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GameConfig {

    @Value("${hishab.co.dice.game.start-score}")
    public Integer startScore;

    @Value("${hishab.co.dice.game.penalty-score}")
    public Integer penaltyScore;

    @Value("${hishab.co.dice.game.winning-score}")
    public Integer winningScore;

    @PostConstruct
    public void afterConstruction() {
        if (startScore == null || !(startScore >= 1 && startScore <= 6)) {
            startScore = 6; // default
        }

        if (penaltyScore == null || !(penaltyScore >= 1 && penaltyScore <= 6)) {
            penaltyScore = 4; // default
        }

        if (winningScore == null || !(winningScore >= 1 && winningScore <= 50)) {
            penaltyScore = 25; // default
        }
    }
}
