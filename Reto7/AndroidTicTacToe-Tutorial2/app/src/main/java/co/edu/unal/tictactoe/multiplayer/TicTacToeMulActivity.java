package co.edu.unal.tictactoe.multiplayer;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import co.edu.unal.tictactoe.BoardView;
import co.edu.unal.tictactoe.R;
import co.edu.unal.tictactoe.multiplayer.model.GameModel;

public class TicTacToeMulActivity extends AppCompatActivity {
    private TicTacToeMulViewModel viewModel;
    private TextView mInfoTextView;
    private TextView textScore;
    private BoardView mBoardView;
    private boolean mSoundOn;
    private SharedPreferences mPrefs;
    MediaPlayer mHumanMediaPlayer;
    MediaPlayer mComputerMediaPlayer;
    private static final String TAG = "TicTacToeMulActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);
        configView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void configView() {
        viewModel = new ViewModelProvider(this).get(TicTacToeMulViewModel.class);
        textScore = findViewById(R.id.score);
        mInfoTextView = findViewById(R.id.information);

        //Player
        Bundle extras = getIntent().getExtras();
        boolean isCreator;
        if (extras != null) {
            isCreator = extras.getBoolean("creator");
            if (isCreator) {
                viewModel.setPlayerType(TicTacToeMulViewModel.playerType.PLAYER_1);
                String code = extras.getString("code");
                viewModel.createGame(code);
                mInfoTextView.setText(R.string.not_player);
                viewModel.waitSecondPlayer(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            boolean secondP = dataSnapshot.getValue(Boolean.class);
                            if (secondP) {
                                viewModel.setSecondPlayer(true);
                                verifyTurnText();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled: ");
                    }
                });
            } else {
                viewModel.setPlayerType(TicTacToeMulViewModel.playerType.PLAYER_2);
                String code = extras.getString("code");
                viewModel.setCode(code);
                viewModel.setSecondPlayer(true);
                verifyTurnText();
                getEnemyMove();
            }
        }
        viewModel.config();
        mBoardView = findViewById(R.id.board);
        mBoardView.setGame(viewModel.getGame());
        mBoardView.setOnTouchListener(mOnTouchListener);
        changeTextScore();
    }

    private void verifyTurnText() {
        if (viewModel.isYourTurn()) {
            mInfoTextView.setText(R.string.user_turn);
        } else {
            mInfoTextView.setText(R.string.other_player_turn);
        }
    }

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int col = (int) event.getX() / mBoardView.getBoardCellWidth();
            int row = (int) (event.getY() / mBoardView.getBoardCellHeight());
            int pos = row * 3 + col;
            if (viewModel.isSecondPlayer()) {
                if (viewModel.isYourTurn() && !viewModel.ismGameOver() && viewModel.setMove(viewModel.getMyCharacter(), pos)) {
                    mBoardView.invalidate();
                    viewModel.sendMove(pos);
                    //mHumanMediaPlayer.start();
                    int winner = viewModel.checkForWinner();
                    roundCheck(winner);
                    getEnemyMove();
                }
            } else {
                mInfoTextView.setText(R.string.not_player);
            }

            return false;
        }
    };


    private void roundCheck(int winner) {
        if (winner == 0) {
            verifyTurnText();
        } else {
            if (winner == 1) {
                mInfoTextView.setText(R.string.mTie);
                viewModel.setScore(1);
            } else if (winner == 2) {
                mInfoTextView.setText(R.string.player_1_won);
                viewModel.setScore(0);
            } else {
                mInfoTextView.setText(R.string.player_2_won);
                viewModel.setScore(2);
            }
            viewModel.setGameOver(true);
            changeTextScore();
        }
    }

    private void getEnemyMove() {

        viewModel.getEnemyMove(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int pos = dataSnapshot.child(GameModel.KEY_POS).getValue(Integer.class);

                if ((pos!=-1)&&viewModel.setMove(viewModel.getEnemyCharacter(), pos)) {
                    int winner = viewModel.checkForWinner();
                    roundCheck(winner);
                    mBoardView.invalidate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //mComputerMediaPlayer.start();

    }

    private void changeTextScore() {
        String aux = getString(R.string.player_1)+" " + viewModel.getScore(0) + "  " +
                getString(R.string.tie) + viewModel.getScore(1) + "  " + getString(R.string.player_2) + " "+viewModel.getScore(2);
        textScore.setText(aux);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewModel.getPlayerType() == TicTacToeMulViewModel.playerType.PLAYER_1) {
            //TODO:server
        } else {
            //TODO:client

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHumanMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.h1);
        mComputerMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.h2);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHumanMediaPlayer.release();
        mComputerMediaPlayer.release();
    }

}


