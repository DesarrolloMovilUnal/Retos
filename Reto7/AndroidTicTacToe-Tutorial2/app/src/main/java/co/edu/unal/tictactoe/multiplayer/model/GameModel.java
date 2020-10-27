package co.edu.unal.tictactoe.multiplayer.model;

public class GameModel {
    private String code;
    private int pos;
    private boolean active;
    private boolean secondPlayer;
    public static String KEY_GAME = "Game";
    public static String KEY_POS = "pos";
    public static String KEY_IS_ACTIVE = "active";
    public static String KEY_CODE = "code";
    public static String KEY_SECOND_PLAYER = "secondPlayer";
    public GameModel(String code, int pos, boolean active, boolean secondPlayer) {
        this.code = code;
        this.pos = pos;
        this.active = active;
        this.secondPlayer = secondPlayer;
    }

    public GameModel() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(boolean secondPlayer) {
        this.secondPlayer = secondPlayer;
    }
}
