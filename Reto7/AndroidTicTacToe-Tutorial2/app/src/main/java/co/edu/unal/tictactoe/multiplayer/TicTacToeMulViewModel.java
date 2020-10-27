package co.edu.unal.tictactoe.multiplayer;

import android.util.EventLog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.UnknownHostException;
import java.util.EventListener;

import co.edu.unal.tictactoe.TicTacToeGame;
import co.edu.unal.tictactoe.multiplayer.model.GameModel;

public class TicTacToeMulViewModel extends ViewModel {
    private TicTacToeGame mGame;
    private boolean mGameOver;
    private int[] score;
    private int turn;
    private playerType mPlayerType;
    private boolean isYourTurn;
    private char myCharacter;
    private char enemyCharacter;
    private String code;
    private boolean secondPlayer;


    private static final String TAG = "TicTacToeMulViewModel";


    enum playerType {PLAYER_1, PLAYER_2}

    protected void config() {
        mGame = new TicTacToeGame();
        mGameOver = false;
        score = new int[]{0, 0, 0};
        turn = 0;
    }

    public void setGameOver(boolean mGameOver) {
        this.mGameOver = mGameOver;
    }

    public TicTacToeGame getGame() {
        return mGame;
    }

    public playerType getPlayerType() {
        return mPlayerType;
    }

    public boolean isSecondPlayer() {
        return secondPlayer;
    }

    public void setPlayerType(playerType mPlayerType) {
        this.mPlayerType = mPlayerType;
        if (this.mPlayerType == playerType.PLAYER_1){
            this.isYourTurn = true;
            myCharacter = TicTacToeGame.HUMAN_PLAYER;
            enemyCharacter = TicTacToeGame.COMPUTER_PLAYER;
        }else {
            this.isYourTurn =false;
            myCharacter = TicTacToeGame.COMPUTER_PLAYER;
            enemyCharacter = TicTacToeGame.HUMAN_PLAYER;
        }
    }

    public boolean ismGameOver() {
        return mGameOver;
    }

    public int[] getScore() {
        return score;
    }

    public int getTurn() {
        return turn;
    }

    public String getCode() {
        return code;
    }

    public void setSecondPlayer(boolean secondPlayer) {
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference(GameModel.KEY_GAME).child(code);
        mRef.child(GameModel.KEY_SECOND_PLAYER).setValue(true);
        mRef.child(GameModel.KEY_IS_ACTIVE).setValue(false);
        this.secondPlayer = secondPlayer;
    }


    public int getScore(int index) {
        return score[index];
    }

    protected int checkForWinner() {
        return mGame.checkForWinner();
    }

    protected boolean setMove(char player, int location) {
        boolean ans = mGame.setMove(player, location);
        if (ans)
            isYourTurn = !isYourTurn;
        return ans;
    }

    public void setCode(String code) {
        this.code = code;
    }

    protected void setScore(int index) {
        score[index]++;
    }

    public boolean isYourTurn() {
        return isYourTurn;
    }

    public char getMyCharacter() {
        return myCharacter;
    }

    public char getEnemyCharacter() {
        return enemyCharacter;
    }
    public void createGame(String code){
        this.code = code;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference(GameModel.KEY_GAME);
        GameModel gameModel = new GameModel(code,-1,true,false);
        mRef.child(code).setValue(gameModel);
    }
    public void sendMove(int pos){
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference(GameModel.KEY_GAME);
        mRef.child(code).child(GameModel.KEY_POS).setValue(pos);
    }
    public void destroy(){

    }
    public void getEnemyMove(final ValueEventListener listener){
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference(GameModel.KEY_GAME).child(code);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onDataChange(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onCancelled(databaseError);
            }
        });
    }
    public void waitSecondPlayer(final ValueEventListener listener){
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference(GameModel.KEY_GAME).child(code).child(GameModel.KEY_SECOND_PLAYER);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onDataChange(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onCancelled(databaseError);
            }
        });
    }


}
