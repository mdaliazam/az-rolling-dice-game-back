package hishab.co.games.dice.service.impl;

import hishab.co.games.dice.model.RollingDice;
import hishab.co.games.dice.service.DiceRollerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HishabDiceRollerService implements DiceRollerService {

    @Value("${hishab.co.dice.game.roller.endpoint:http://developer-test.hishab.io/api/v1/roll-dice}")
    private String diceRollerEndpoint;
    private RestTemplate restTemplate;

    public HishabDiceRollerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Integer roll() {
        RollingDice dice = null;
        try {
            dice = restTemplate.getForObject(
                    this.diceRollerEndpoint, RollingDice.class);
        } catch (Exception ex) {
            // TODO log here
        }
        return dice == null ? null : dice.getScore();
    }
}
