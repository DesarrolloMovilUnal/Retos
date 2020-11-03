package co.edu.unal.myapplicationsqlite.View;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import co.edu.unal.myapplicationsqlite.Model.ContactModel;
import co.edu.unal.myapplicationsqlite.R;
import co.edu.unal.myapplicationsqlite.controller.DBHelper;
import co.edu.unal.myapplicationsqlite.viewModel.MainActivityViewModel;


public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    EditText searchEditText;
    Button searchButton;
    ListView mListView;
    MainActivityViewModel viewModel;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configView();

    }

    private void configView(){
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.setDB(new DBHelper(this));
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.buttonSearch);
        mListView = findViewById(R.id.listView1);
        radioGroup = findViewById(R.id.radioGroupSearch);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateEditContactActivity.class);
                intent.putExtra(CreateEditContactActivity.IS_NEW_DATA,true);
                startActivity(intent);
            }
        });
        setListView();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = searchEditText.getText().toString();
                int radioId = radioGroup.getCheckedRadioButtonId();
                switch (radioId){
                    case R.id.radioButtonAll:
                        setListView();
                        break;
                    case R.id.radioButtonName:
                        setListViewName(value);
                        break;
                    case R.id.radioButtonClass:
                        setListViewClass(value);
                        break;
                }
            }
        });
    }

    private void setListView(){
        ArrayList arrayList = viewModel.getDB().getAllContacts();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        mListView.setAdapter(arrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idToSearch = position+1;
                Intent intent = new Intent(getApplicationContext(), DisplayContactActivity.class);
                intent.putExtra(CreateEditContactActivity.ID,idToSearch);
                startActivity(intent);
            }
        });
    }
    private void setListViewName(String name){
        ArrayList arrayList = viewModel.getDB().getDataByName(name);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        mListView.setAdapter(arrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idToSearch = position+1;
                Intent intent = new Intent(getApplicationContext(), DisplayContactActivity.class);
                intent.putExtra(CreateEditContactActivity.ID,idToSearch);
                startActivity(intent);
            }
        });
    }
    private void setListViewClass(String classification){
        ArrayList arrayList = viewModel.getDB().getDataByClass(ContactModel.stringToClass(classification).toString());
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        mListView.setAdapter(arrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idToSearch = position+1;
                Intent intent = new Intent(getApplicationContext(), DisplayContactActivity.class);
                intent.putExtra(CreateEditContactActivity.ID,idToSearch);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}