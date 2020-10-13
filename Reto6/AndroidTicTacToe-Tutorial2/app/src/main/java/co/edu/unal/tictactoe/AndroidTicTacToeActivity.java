package co.edu.unal.tictactoe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidTicTacToeActivity extends AppCompatActivity implements DifficultyDialogFragment.DialogListener {
    private TicTacToeGame mGame;
    private Button mBoardButtons[];
    private TextView mInfoTextView;
    private boolean mGameOver;
    private int[] score;
    private TextView textScore;
    private int turn;
    private BoardView mBoardView;
    private boolean mSoundOn;
    private SharedPreferences mPrefs;
    private static final String TAG = "AndroidTicTacToeActivit";
    MediaPlayer mHumanMediaPlayer;
    MediaPlayer mComputerMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);
        score = new int[3];
        turn = 0;
        textScore = findViewById(R.id.score);
        mBoardButtons = new Button[TicTacToeGame.BOARD_SIZE];
        /*
        mBoardButtons[0] = findViewById(R.id.one);
        mBoardButtons[1] = findViewById(R.id.two);
        mBoardButtons[2] = findViewById(R.id.three);
        mBoardButtons[3] = findViewById(R.id.four);
        mBoardButtons[4] = findViewById(R.id.five);
        mBoardButtons[5] = findViewById(R.id.six);
        mBoardButtons[6] = findViewById(R.id.seven);
        mBoardButtons[7] = findViewById(R.id.eight);
        mBoardButtons[8] = findViewById(R.id.nine);*/

        mInfoTextView = findViewById(R.id.information);

        mGame = new TicTacToeGame();
        mBoardView = findViewById(R.id.board);
        mBoardView.setGame(mGame);
        mBoardView.setOnTouchListener(mOnTouchListener);
        new WelcomeDialogFragment().show(getSupportFragmentManager(), "Welcome");
        // Restore the scores from the persistent preference data source
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mSoundOn = mPrefs.getBoolean("sound", true);
        String difficultyLevel = mPrefs.getString("difficulty_level", getResources().getString(R.string.hard));
        if (difficultyLevel.equals(getResources().getString(R.string.easy)))
            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy);
        else if (difficultyLevel.equals(getResources().getString(R.string.hard)))
            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Harder);
        else
            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);
        startNewGame();
        changeTextScore();

    }

    private void startNewGame() {
        mGame.clearBoard();
        mGameOver = false;
        mBoardView.invalidate();
        mInfoTextView.setText(R.string.you_go_first);
        if (turn++ % 2 != 0) {
            mInfoTextView.setText(R.string.android_turn);
            int move = mGame.getComputerMove();
            setMove(TicTacToeGame.COMPUTER_PLAYER, move);
        }
    }

    @Override
    public void option(TicTacToeGame.DifficultyLevel option) {
        String level = "";
        switch (option) {
            case Easy:
                level = getString(R.string.easy);
                break;
            case Harder:
                level = getString(R.string.hard);
                break;
            case Expert:
                level = getString(R.string.expert);
                break;
        }
        Toast.makeText(this, level, Toast.LENGTH_SHORT).show();
        mGame.setDifficultyLevel(option);
    }

    private boolean setMove(char player, int location) {
        if (mGame.setMove(player, location)) {
            mBoardView.invalidate();
            if (mSoundOn)
                mHumanMediaPlayer.start();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean aux = false;
        switch (item.getItemId()) {
            case R.id.new_game:
                aux = true;
                startNewGame();
                break;
            case R.id.options:
                aux = true;
                startActivityForResult(new Intent(this, Settings.class), 0);
                break;
            case R.id.quit:
                aux = true;
                new QuitDialogFragment().show(getSupportFragmentManager(), "quit fragment");
                break;
            default:
                aux = false;
        }

        return aux;
    }

    private void changeTextScore() {
        String aux = getString(R.string.human) + score[0] + " " +
                getString(R.string.tie) + score[1] + " " + getString(R.string.android) + score[2];
        textScore.setText(aux);
    }

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int col = (int) event.getX() / mBoardView.getBoardCellWidth();
            int row = (int) (event.getY() / mBoardView.getBoardCellHeight());
            int pos = row * 3 + col;
            if (!mGameOver && setMove(TicTacToeGame.HUMAN_PLAYER, pos)) {

                Handler handler = new Handler();

                int winner = mGame.checkForWinner();
                if (winner == 0) {
                    mInfoTextView.setText(R.string.android_turn);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int move = mGame.getComputerMove();
                            setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                            mBoardView.invalidate();
                            int winnerA = mGame.checkForWinner();
                            if (mSoundOn)
                                mComputerMediaPlayer.start();
                            roundCheck(winnerA);
                        }
                    }, 500);
                }else {
                    roundCheck(winner);
                }


            }
            return false;
        }
    };

    private void roundCheck(int winner) {
        if (winner == 0) {
            mInfoTextView.setText(R.string.user_turn);
        } else {
            if (winner == 1) {
                mInfoTextView.setText(R.string.mTie);
                score[1]++;
            } else if (winner == 2) {
                String defaultMessage = getResources().getString(R.string.you_won);
                mInfoTextView.setText(mPrefs.getString("victory_message", defaultMessage));
                score[0]++;
            } else {
                mInfoTextView.setText(R.string.android_won);
                score[2]++;
            }
            mGameOver = true;
            changeTextScore();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_CANCELED) {
            mSoundOn = mPrefs.getBoolean("sound", true);
            String difficultyLevel = mPrefs.getString("difficulty_level", getResources().getString(R.string.hard));
            if (difficultyLevel.equals(getResources().getString(R.string.easy)))
                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy);
            else if (difficultyLevel.equals(getResources().getString(R.string.hard)))
                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Harder);
            else
                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);
        }
    }
}
