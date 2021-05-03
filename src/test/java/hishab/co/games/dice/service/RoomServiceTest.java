package hishab.co.games.dice.service;

import hishab.co.games.dice.model.GameRoom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Test
    void testAddRoomThatReturnsWithId() {
        GameRoom added = this.roomService.addRoom(new GameRoom("test"));
        assertNotNull(added.getId(), "Service didn't assign a room id");
    }

    @Test
    void testAddedRoomCanBeRetrievedWithId() {
        GameRoom added = this.roomService.addRoom(new GameRoom("test"));

        GameRoom existing = this.roomService.getRoomById(added.getId());
        assertNotNull(existing, "Newly added room could not be retrieved with the room id");
        assertEquals(existing.getId(), added.getId(), "Both id are not equal");
    }

}
