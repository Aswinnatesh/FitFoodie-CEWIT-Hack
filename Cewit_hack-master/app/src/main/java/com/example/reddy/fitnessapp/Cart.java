package com.example.reddy.fitnessapp;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by reddy on 17-02-2018.
 */

public class Cart {
    Map<Product,Integer> map;
    Integer m_val = 0;

    Cart(){
        map = new LinkedHashMap<>();
    }

    void addToCart(Product product){
        if(map.containsKey(product)){
            map.put(product,map.get(product)+1);
        }
        else{
            map.put(product,1);
        }

        m_val += product.getValue();
    }

    int getQuantity(Product product){
        return map.get(product);
    }

    Set getProducts(){
        return map.keySet();
    }

    void Empty(){
        map.clear();
        m_val = 0;
    }

    Integer getValue(){
        return m_val;
    }
}
