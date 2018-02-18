package com.example.reddy.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DataActivity extends AppCompatActivity {
    TextView mResponse;
    Cart mCart = new Cart();
    static Cart m_cart = new Cart();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        mResponse = (TextView)findViewById(R.id.response);
        Product products[] = {
                new Product("Chapati", 10),
                new Product("Rice", 20),
                new Product("Potatoes",5)
        };

        LinearLayout list = (LinearLayout)findViewById(R.id.list);

        for(int i=0;i<products.length;i++){
            final Button btn = new Button(this);
            btn.setText(products[i].getName()+" "+products[i].getValue()+" $");
            btn.setTag(products[i]);
            btn.setTextSize(24);
            btn.setPadding(10,10,10,10);
            btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200, Gravity.CENTER);
            layoutParams.setMargins(20,50,20,50);
            btn.setLayoutParams(layoutParams);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button btn = (Button)view;
                    Product product = (Product)btn.getTag();
                    mCart.addToCart(product);
                    mResponse.setText("Total Cart value = " + String.format("%d",mCart.getValue()) + " $");
                }
            });
            list.addView(btn);
        }
    }

    public void reset(View view){
        mResponse.setText("Total cart value = 0$");
        mCart.Empty();
    }

    public void viewCart(View view){
        Intent intent = new Intent(this,Viewcart.class);
        m_cart = mCart;
        startActivity(intent);
    }
}
