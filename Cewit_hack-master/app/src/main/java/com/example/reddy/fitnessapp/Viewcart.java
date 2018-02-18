package com.example.reddy.fitnessapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Iterator;
import java.util.Set;

import static com.example.reddy.fitnessapp.MainActivity.id;

public class Viewcart extends AppCompatActivity {
    int total_cal, total_protein, total_ch, total_vita, total_vitc, total_fat;
    int std_cal=1600;
    int std_protein=50000;
    int std_ch=300;
    int std_vita=1;
    int std_vitc=90;
    int std_fat=70000;
    String deficiency="";
    String danger="";
    Button mPay;
    TextView mViewStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcart);
        mViewStatus = (TextView)findViewById(R.id.view_status);

        Cart cart = DataActivity.m_cart;
        LinearLayout cart_layout = (LinearLayout) findViewById(R.id.cart);
        Set<Product> products = cart.getProducts();
        Iterator iterator = products.iterator();
        while (iterator.hasNext()) {
            System.out.println("Entered The iterator");
            Product product = (Product) iterator.next();
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(linearLayout.HORIZONTAL);
            final TextView name = new TextView(this);
            TextView quantity = new TextView(this);
            name.setText(product.getName());
            quantity.setText(Integer.toString(cart.getQuantity(product)));
            linearLayout.addView(name);
            linearLayout.addView(quantity);
            name.setTextSize(20);
            quantity.setTextSize(20);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200, Gravity.CENTER);
            layoutParams.setMargins(20, 10, 20, 20);
            linearLayout.setLayoutParams(layoutParams);
            name.setLayoutParams(new TableLayout.LayoutParams(0, ActionBar.LayoutParams.WRAP_CONTENT, 1));
            quantity.setLayoutParams(new TableLayout.LayoutParams(0, ActionBar.LayoutParams.WRAP_CONTENT, 1));
            name.setGravity(Gravity.CENTER);
            quantity.setGravity(Gravity.CENTER);
            cart_layout.addView(linearLayout);

            String url = "https://api.nutritionix.com/v1_1/search/" + name.getText().toString() + "?cal_min=0&cal_max=50000&fields=item_name&appId=17448a03&appKey=302371d7d8284fb632a1f036ba609472";
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray hits = response.getJSONArray("hits");
                        System.out.println(hits.length());
                        for (int i = 0; i < hits.length(); i++) {
                            JSONObject obj = hits.getJSONObject(i);
                            System.out.println("Hello I am in");
                            JSONObject obj2 = obj.getJSONObject("fields");
                            String item_name = obj2.getString("item_name");
                            if (item_name.equals(name.getText().toString())) {
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
            }

            , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

                System.out.println(id);


            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(jsonObjectRequest);

        }

    }

    private void getFoodDetails(String id) {

        String url2 = "https://api.nutritionix.com/v1_1/item?id=" + id + "&appId=17448a03&appKey=302371d7d8284fb632a1f036ba609472";
        final JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println("Got the response");
                    String calories = response.getString("nf_calories");
                    String fat = response.getString("nf_total_fat");
                    String cholestrol = response.getString("nf_cholesterol");
                    String proteins = response.getString("nf_protein");
//                    System.out.println(proteins);
                    String vitaminA = response.getString("nf_vitamin_a_dv");
                    String vitaminC = response.getString("nf_vitamin_c_dv");

                    total_cal+=Integer.parseInt(calories);
                    if(vitaminA != "null") total_vita+=Integer.parseInt(vitaminA);
                    if(vitaminC != "null") total_vitc+=Integer.parseInt(vitaminC);
//
                    total_fat+=Integer.parseInt(fat);
                    total_ch+=Integer.parseInt(cholestrol);
                    if(proteins != "null") total_protein+=Integer.parseInt(proteins);
//

                    if(total_cal>std_cal || total_ch>std_ch || total_fat>std_fat || total_protein>std_protein || total_vita>std_vita
                            || total_vitc>std_vitc)
                    {
                        mViewStatus.setText("Your diet is imbalanced");

                    }
                    else{
//                        Toast.makeText(getApplicationContext(), "Diet is balanced", Toast.LENGTH_SHORT).show();
                        mViewStatus.setText("Your diet is balanced");
                    }


                 //   mDetails.setText(calories);

                } catch (JSONException e) {
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
