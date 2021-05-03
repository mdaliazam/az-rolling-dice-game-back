package hishab.co.games.dice.service;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class DiceRollerServiceTest {

    @Autowired
    private DiceRollerService rollerService;

    @Test
    public void testDiceRollingWhenConnected() {
        Integer score = this.rollerService.roll();
        assertNotNull(score, "Service could not return value even when connected");
    }

    @Test
    @Disabled
    public void testDiceRollingWhenDisconnected() {
        Integer score = this.rollerService.roll();
        assertNull(score, "Score must be null when disconnected");
    }
}
