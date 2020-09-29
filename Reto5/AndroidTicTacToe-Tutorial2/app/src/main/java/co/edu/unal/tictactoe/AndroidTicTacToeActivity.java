package co.edu.unal.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
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
        startNewGame();
        changeTextScore();

    }

    private void startNewGame() {
        mGame.clearBoard();
        mGameOver = false;
        mBoardView.invalidate();
        /*
        int i = 0;
        for (Button m : mBoardButtons) {
            m.setText("");
            m.setEnabled(true);
            m.setOnClickListener(new ButtonClickListener(i++));
        }*/
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

 /*   private class ButtonClickListener implements View.OnClickListener {
        int location;

        public ButtonClickListener(int location) {
            this.location = location;
        }

        @Override
        public void onClick(View v) {
            if (mBoardButtons[location].isEnabled() && !mGameOver) {
                setMove(TicTacToeGame.HUMAN_PLAYER, location);

                int winner = mGame.checkForWinner();
                if (winner == 0) {
                    mInfoTextView.setText(R.string.android_turn);
                    int move = mGame.getComputerMove();
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    winner = mGame.checkForWinner();
                }
                if (winner == 0) {
                    mInfoTextView.setText(R.string.user_turn);
                } else {
                    if (winner == 1) {
                        mInfoTextView.setText(R.string.mTie);
                        score[1]++;
                    } else if (winner == 2) {
                        mInfoTextView.setText(R.string.you_won);
                        score[0]++;
                    } else {
                        mInfoTextView.setText(R.string.android_won);
                        score[2]++;
                    }
                    mGameOver = true;
                    changeTextScore();

            }
        }

    }}*/

    /*
        private void setMove(char player, int location) {
            mGame.setMove(player, location);
            mBoardButtons[location].setEnabled(false);
            mBoardButtons[location].setText(String.valueOf(player));
            if (player == TicTacToeGame.HUMAN_PLAYER) {
                mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
            } else {
                mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));
            }
        }*/
    private boolean setMove(char player, int location) {
        if (mGame.setMove(player, location)) {
            mBoardView.invalidate();
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
            case R.id.ai_difficulty:
                aux = true;
                new DifficultyDialogFragment().show(getSupportFragmentManager(), "difficulty menu");
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
                int winner = mGame.checkForWinner();
                if (winner == 0) {
                    mInfoTextView.setText(R.string.android_turn);
                    int move = mGame.getComputerMove();
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    winner = mGame.checkForWinner();
                    mComputerMediaPlayer.start();
                }
                if (winner == 0) {
                    mInfoTextView.setText(R.string.user_turn);
                } else {
                    if (winner == 1) {
                        mInfoTextView.setText(R.string.mTie);
                        score[1]++;
                    } else if (winner == 2) {
                        mInfoTextView.setText(R.string.you_won);
                        score[0]++;
                    } else {
                        mInfoTextView.setText(R.string.android_won);
                        score[2]++;
                    }
                    mGameOver = true;
                    changeTextScore();
                }
            }
            return false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mHumanMediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.h1);
        mComputerMediaPlayer =  MediaPlayer.create(getApplicationContext(),R.raw.h2);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHumanMediaPlayer.release();
        mComputerMediaPlayer.release();
    }
}
