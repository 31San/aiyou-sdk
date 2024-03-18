package org.eu.miraikan.aiyou.types.tuning;

public enum State {
    STATE_UNSPECIFIED(0),
    CREATING(1),
    ACTIVE(2),
    FAILED(3);

    private int code;

    State(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public static State fromString(String jsonValue) {
        for (State state : State.values()) {
            if (state.name().equalsIgnoreCase(jsonValue)) {
                return state;
            }
        }
        throw new IllegalArgumentException("No constant with text " + jsonValue + " found");
    }
    // Optional: Add a method to get the enum from the int code if needed
    public static State fromCode(int code) {
        for (State state : State.values()) {
            if (state.getCode() == code) {
                return state;
            }
        }
        throw new IllegalArgumentException("No constant with code " + code + " found");
    }
}