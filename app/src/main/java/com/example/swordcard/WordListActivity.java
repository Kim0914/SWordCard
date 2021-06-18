package com.example.swordcard;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Response;

import java.util.List;

public class WordListActivity extends AppCompatActivity {
    private Button btn_close;
    private Button btn_enter;
    public ListView listview;
    private EditText mWordInput;
    private EditText mMeanInput;
    WordModule newModule = new WordModule(WordListActivity.this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordlist);
        mWordInput = (EditText) findViewById(R.id.wordedit);
        mMeanInput = (EditText) findViewById(R.id.meanedit);

        btn_close = (Button)findViewById(R.id.Close);
        btn_close.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 단어 수동입력 모드
        btn_enter = (Button) findViewById(R.id.enter);
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = mWordInput.getText().toString();
                String mean = mMeanInput.getText().toString();

                if(word.length() <= 0 || mean.length() <= 0){
                    Toast.makeText(getApplicationContext(), "다시 입력해주세요.",Toast.LENGTH_LONG).show();
                }
                else{
                    WordEntry entry = new WordEntry(word,mean);
                    newModule.addWord(entry);
                    Toast.makeText(getApplicationContext(), "단어가 추가되었습니다!!.",Toast.LENGTH_LONG).show();
                }
            }
        });

        System.out.println("Works");
        WordModule wordModule = new WordModule(this);
        List<WordEntry> test_list = wordModule.getAllWords();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, test_list);
        listview = (ListView)findViewById(R.id.wordlist);
        listview.setAdapter(adapter);
    }

    /*void volley(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="127.0.0.1:80";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println("VOLLEY:: Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("VOLLEY:: WRONG WORKS");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }*/
}
