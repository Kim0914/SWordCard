package com.example.swordcard;

import android.content.Context;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class WordsCloud {
    Context context;
    WordModule local_words;
    String ip = "http://10.0.2.2:3000";
    public WordsCloud(Context context){
        this.context= context;
        local_words = new WordModule(context);
    }
    void upload(String id){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ip+"/list/"+id;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        local_words.fromJSON(response);
                        System.out.println("VOLLEY"+response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("VOLLEY:: WRONG WORKS");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("id",id);
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                System.out.println("upload"+local_words.toJSON());
                return local_words.toJSON().getBytes();
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    void download(String id){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ip+"/list/"+id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        local_words.fromJSON(response);
                        System.out.println("VOLLEY"+response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("VOLLEY:: WRONG WORKS");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}