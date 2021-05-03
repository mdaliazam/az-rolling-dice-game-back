package hishab.co.games.dice.exception;

public class RoomFullException extends ApiException {
    public RoomFullException(String message) {
        super(message);
    }

    public RoomFullException(String message, Throwable cause) {
        super(message, cause);
    }
}
