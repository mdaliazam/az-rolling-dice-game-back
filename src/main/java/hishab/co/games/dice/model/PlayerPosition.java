package hishab.co.games.dice.model;

public enum PlayerPosition {
    NORTH(0),
    EAST(1),
    SOUTH(2),
    WEST(3);


    public PlayerPosition next(int count) {
        int next = this.position + 1;
        if (next >= count) return NORTH;
        return findByPosition(next);
    }

    private int position;

    PlayerPosition(int position) {
        this.position = position;
    }

    private PlayerPosition findByPosition(int position) {
        for (PlayerPosition playerPosition : PlayerPosition.values()) {
            if (playerPosition.position == position) {
                return playerPosition;
            }
        }
        return NORTH;
    }
}
