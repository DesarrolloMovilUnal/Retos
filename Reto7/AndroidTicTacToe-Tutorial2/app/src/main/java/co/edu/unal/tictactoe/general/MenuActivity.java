package co.edu.unal.tictactoe.general;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import co.edu.unal.tictactoe.R;
import co.edu.unal.tictactoe.multiplayer.MenuMulPActivity;
import co.edu.unal.tictactoe.multiplayer.TicTacToeMulActivity;
import co.edu.unal.tictactoe.single.AndroidTicTacToeActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    private Button sPlayer;
    private Button mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        sPlayer = findViewById(R.id.single_op_bt);
        sPlayer.setOnClickListener(this);
        mPlayer = findViewById(R.id.multi_op_bt);
        mPlayer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.single_op_bt:
                intent = new Intent(this, AndroidTicTacToeActivity.class);
                startActivity(intent);
                break;
            case R.id.multi_op_bt:
                intent = new Intent(this, MenuMulPActivity.class);
                startActivity(intent);
                break;
        }
    }
}