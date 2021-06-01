package com.example.swordcard;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WordListActivity extends MainActivity {
    private Button btn_close;
    private Button btn_enter;
    private EditText mWordInput;
    private EditText mMeanInput;

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
                    list.add(entry);
                    Toast.makeText(getApplicationContext(), "단어가 추가되었습니다!!.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
