package hishab.co.games.dice.service.impl;

import hishab.co.games.dice.config.GameConfig;
import hishab.co.games.dice.model.Player;
import hishab.co.games.dice.model.RollingResult;
import hishab.co.games.dice.service.DiceRollerService;
import hishab.co.games.dice.service.ScorerService;
import org.springframework.stereotype.Service;

@Service
public class RollingDiceScorerService implements ScorerService {

    private GameConfig config;
    private DiceRollerService diceRollerService;

    public RollingDiceScorerService(GameConfig config,
                                    DiceRollerService rollerService) {
        this.config = config;
        this.diceRollerService = rollerService;
    }

    /**
     * @inheritDoc
     */
    @Override
    public RollingResult rollAndSetScore(Player player) {

        Integer currentScore = this.diceRollerService.roll();
        if (currentScore == null) {
            return null;
        }
        player.setCurrentScore(currentScore);
        if (currentScore.intValue() == config.penaltyScore) {
            if (player.isStartRolling()) {
                player.setTotalScore(Math.max(0, player.getTotalScore() - config.penaltyScore));
                player.setStartRolling(player.getTotalScore() > 0);
            }
            Player nextPlayer = player.getRoom().next(player.getPosition());
            return new RollingResult(false, 0, currentScore,
                    player.clone(), nextPlayer != null ? nextPlayer.clone() : null);
        }

        Player next;
        if (currentScore.intValue() == config.startScore && !player.isStartRolling()) {
            player.setStartRolling(true);
            return new RollingResult(false, 0, currentScore, player.clone(), player.clone());
        } else if (currentScore.intValue() == config.startScore) {
            player.setTotalScore(player.getTotalScore() + currentScore);
            return new RollingResult(player.getTotalScore() >= config.winningScore, player.getTotalScore(),
                    currentScore, player.clone(), player.clone());
        }

        next = player.getRoom().next(player.getPosition());
        int score = player.isStartRolling() ? currentScore : 0;
        player.setTotalScore(player.getTotalScore() + score);
        return new RollingResult(player.getTotalScore() >= config.winningScore, player.getTotalScore(),
                currentScore, player.clone(), next.clone());
    }
}
