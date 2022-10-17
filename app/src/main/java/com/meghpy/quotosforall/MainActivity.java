package com.meghpy.quotosforall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    HashMap<String, String> hashMap;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);





            String url= "https://dummyjson.com/quotes";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("quotes");
                    for (int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.d("serverRes", response.toString());
                        String quoto = jsonObject.getString("quote");
                        String author = jsonObject.getString("author");

                        hashMap = new HashMap<>();
                        hashMap.put("quoto",quoto);
                        hashMap.put("author",author);
                        arrayList.add(hashMap);


                    }
                    if (arrayList.size()>0){
                        MyAdapter myAdapter = new MyAdapter();
                        gridView.setAdapter(myAdapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonObjectRequest);



    }

    public class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
          View myView =  inflater.inflate(R.layout.design,null);


            TextView quoteText = myView.findViewById(R.id.quoteText);
            TextView quoteAuthor = myView.findViewById(R.id.quoteAuthor);
            ImageButton copy = myView.findViewById(R.id.copy);
            ImageButton share = myView.findViewById(R.id.share);

            hashMap = arrayList.get(position);
            String quoto = hashMap.get("quoto");
            String author = hashMap.get("author");

            quoteText.setText(quoto);
            quoteAuthor.setText(author);

            return myView;
        }
    }




}