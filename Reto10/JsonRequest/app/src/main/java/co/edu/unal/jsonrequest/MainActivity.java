package co.edu.unal.jsonrequest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mQueue;
    private ListView mListView;
    private ArrayList<Data> arrayData;
    private ArrayList<String> nameArray;
    private static String URL = "https://www.datos.gov.co/resource/drfm-i22d.json";
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configView();
    }

    private void configView() {
        mQueue = Volley.newRequestQueue(this);
        mListView = findViewById(R.id.listView1);
        arrayData = new ArrayList<>();
        nameArray = new ArrayList<>();
        jsonParse();
    }

    private void setListView() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nameArray);
        mListView.setAdapter(arrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = nameArray.get(position);
                jsonParseName(name);
            }
        });
    }

    private void jsonParse() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String toAdd = data.getString("ubicaci_n");
                                if (!nameArray.contains(toAdd)) {
                                    nameArray.add(toAdd);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        setListView();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request);
    }

    private void jsonParseName(String name) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL + "?ubicaci_n=" + name, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        StringBuilder text= new StringBuilder();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String month = data.getString("mes");
                                String average = data.getString("n_de_conexiones_promedio");
                                String bandwidth = data.getString("consumo_de_ancho_de_banda");
                                text.append(month).append(": promedio de conexiones: ").append(average).append(", Consumo de banda: ").append(bandwidth).append("\n\n");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        MyDialogFragment dialogFragment = createDialog(text.toString());
                        dialogFragment.show(getSupportFragmentManager(),"MyFragmentDialog");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request);
    }

    private MyDialogFragment createDialog(String textToAppend) {
        MyDialogFragment myDialogFragment = new MyDialogFragment();
        Bundle args = new Bundle();
        args.putString("text", textToAppend);
        myDialogFragment.setArguments(args);
        return myDialogFragment;
    }

}