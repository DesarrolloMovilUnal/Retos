package co.edu.unal.tictactoe.multiplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import co.edu.unal.tictactoe.R;
import co.edu.unal.tictactoe.multiplayer.model.GameModel;

public class GameListActivity extends AppCompatActivity {
    private ListView mListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList = new ArrayList<>();
    private static final String TAG = "GameListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference(GameModel.KEY_GAME);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);

        mListView = findViewById(R.id.listViewGameList);
        mListView.setAdapter(adapter);
        Query codes = mRef.orderByChild(GameModel.KEY_CODE);
        codes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot post: dataSnapshot.getChildren()){
                    String val = post.getKey();
                    if (post.child(GameModel.KEY_IS_ACTIVE).getValue(Boolean.class))
                        arrayList.add(val);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String val = adapter.getItem(position);
                Intent intent = new Intent(GameListActivity.this, TicTacToeMulActivity.class);
                intent.putExtra("code",val);
                intent.putExtra("creator", false);
                Log.i(TAG, val);
                startActivity(intent);
            }
        });
    }

}