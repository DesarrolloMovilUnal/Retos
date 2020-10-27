package co.edu.unal.tictactoe.multiplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import co.edu.unal.tictactoe.R;
import co.edu.unal.tictactoe.TicTacToeGame;

public class MenuMulPActivity extends AppCompatActivity implements View.OnClickListener, NewGameDialogFragment.DialogListener {
    private Button create;
    private Button join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_mul_p);
        create = findViewById(R.id.create_game_btn);
        create.setOnClickListener(this);
        join = findViewById(R.id.join_btn);
        join.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.create_game_btn:
                openDialog();
                break;
            case R.id.join_btn:
                intent = new Intent(this, GameListActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void openDialog() {
        NewGameDialogFragment newGameDialogFragment = new NewGameDialogFragment();
        newGameDialogFragment.show(getSupportFragmentManager(), "Dialog");
    }

    @Override
    public void onGetCodeText(String code) {
        Intent intent = new Intent(this, TicTacToeMulActivity.class);
        intent.putExtra("creator", true);
        intent.putExtra("code",code);
        startActivity(intent);

    }


}