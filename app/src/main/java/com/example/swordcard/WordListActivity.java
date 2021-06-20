package com.example.swordcard;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    private ListView listview;
    private ArrayAdapter adapter;
    private List<WordEntry> show_list;
    private WordsCloud wc;
    private WordModule wordModule;
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
        wordModule = new WordModule(this);

        wc = new WordsCloud(this);
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
        show_list = wordModule.getAllWords();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, show_list);
        listview = (ListView)findViewById(R.id.wordlist);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deleteAlert("다음 단어를 삭제하시겠습니까?",show_list.get(position).toString(), position);
            }
        });
    }
    private void deleteAlert(String title, String message,int position)
    {
//        WordModule
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("삭제", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        WordEntry word = show_list.get(position);
                        show_list.remove(position);
                        adapter.notifyDataSetChanged();
                        wordModule.removeWord(word.english);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}