package hishab.co.games.dice.component;

import hishab.co.games.dice.model.GameRoom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test-expiry.properties")
public class RoomCacheTest {

    @Autowired
    private RoomCache roomCache;

    @Test
    public void testRoomWasRemovedAfterMaxIdleTimeConfig() {
        GameRoom room = new GameRoom();
        this.roomCache.addRoom(room);
        try {
            Thread.sleep(10000);
        } catch (Exception ex) {
        }
        room = this.roomCache.getRoomById(room.getId());
        assertNull(room, "Room was supposed to be removed after max idle time, but it wasn't");
    }

    @Test
    public void testRoomStillAvailableWhenTouche() {
        GameRoom room = new GameRoom();
        this.roomCache.addRoom(room);
        try {
            Thread.sleep(3000);
        } catch (Exception ignored) {
        }
        this.roomCache.touchRoom(room.getId());
        // sleep some more seconds, room should have 9 seconds' life
        try {
            Thread.sleep(3000);
        } catch (Exception ignored) {
        }
        room = this.roomCache.getRoomById(room.getId());
        assertNotNull(room, "Room was null even after touched");
    }


}
