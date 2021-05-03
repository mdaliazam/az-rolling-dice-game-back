package hishab.co.games.dice.exception;

public class RoomLockedException extends ApiException {
    public RoomLockedException(String message) {
        super(message);
    }

    public RoomLockedException(String message, Throwable cause) {
        super(message, cause);
    }
}
