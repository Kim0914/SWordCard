package com.example.swordcard;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.swordcard.R;
import com.example.swordcard.WordEntry;
import com.example.swordcard.WordModule;
import com.example.swordcard.WordsCloud;

import java.util.ArrayList;
import java.util.List;

public class WordListActivity extends AppCompatActivity {
    public Button btn;
    public Button sync_btn;
    public ListView listview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordlist);

        btn = (Button)findViewById(R.id.Close);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
/*
        btn2 = (Button)findViewById(R.id.query);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println("hello");
            }
        });
*/
//        System.out.println("Works");
        WordModule wordModule = new WordModule(this);
/*
        wordModule.addWord("Test","시험");
        wordModule.addWord("Death","죽음");
        String test_Str = "{\"Happy\":\"행복한\",\"Sad\":\"슬픈\"}";
        wordModule.fromJSON(test_Str);
        wordModule.clear();
*/
        WordsCloud wc = new WordsCloud(this);
//        System.out.println("JSON:"+wordModule.getAllWords());


        sync_btn = (Button)findViewById(R.id.Download);
        sync_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                wc.upload("test");
                wc.download("test");
                finish();
            }
        });

// 리스트 출력
        List<WordEntry> test_list = wordModule.getAllWords();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, test_list);
        listview = (ListView)findViewById(R.id.wordlist);
        listview.setAdapter(adapter);
    }
}