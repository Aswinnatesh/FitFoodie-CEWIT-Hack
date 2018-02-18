package com.example.reddy.fitnessapp;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {

    TextView mFoodItem;
    TextView mPriceItem;
    TextView mDetails;
    Button mSendBtn;
    static String id;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mFoodItem = (TextView)findViewById(R.id.textView_food);
        mPriceItem = (TextView)findViewById(R.id.textView_price);
        mSendBtn = (Button)findViewById(R.id.button_send);
        mDetails = (TextView)findViewById(R.id.textView_res);

        final String food_name = mFoodItem.getText().toString();


        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final String[] id = {null};
                String url = "https://api.nutritionix.com/v1_1/search/"+food_name+"?cal_min=0&cal_max=50000&fields=item_name&appId=17448a03&appKey=302371d7d8284fb632a1f036ba609472";
                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray hits = response.getJSONArray("hits");
                        System.out.println(hits.length());
                        for(int i=0;i<hits.length();i++) {
                            JSONObject obj = hits.getJSONObject(i);
                            System.out.println("Hello I am in");
                            JSONObject obj2 = obj.getJSONObject("fields");
                            String item_name = obj2.getString("item_name");
                            if (item_name.equals(food_name)) {
                                id = obj.getString("_id");
                                System.out.println(id);
                                getFoodDetails(id);
                                break;
                            }
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
//                System.out.println(id);


                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(jsonObjectRequest);

            }
        });

    }

    private void getFoodDetails(String id) {
        String url2 = "https://api.nutritionix.com/v1_1/item?id="+id+"&appId=17448a03&appKey=302371d7d8284fb632a1f036ba609472";
        final JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    System.out.println("Got the response");
                    String calories = response.getString("nf_calories");
                    String fat = response.getString("nf_total_fat");
                    String cholestrol = response.getString("nf_cholesterol");
                    String proteins = response.getString("nf_protein");
                    String vitaminA = response.getString("nf_vitamin_a_dv");
                    String vitaminC = response.getString("nf_vitamin_c_dv");

                    mDetails.setText(calories);

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonObjectRequest1);
    }
}
