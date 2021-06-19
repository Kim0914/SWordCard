package com.example.swordcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Vector;

import static android.view.KeyEvent.ACTION_DOWN;

public class MainActivity extends AppCompatActivity {
    List<WordEntry> list = new Vector<WordEntry>();
    Button btn;
    private Button btn_enter;
    private EditText mWordInput;
    private EditText mMeanInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WordModule a = new WordModule(MainActivity.this);
        list = a.getAllWords(); // db에서 단어 데이터를 받아온다.
        mWordInput = (EditText) findViewById(R.id.wordedit);
        mMeanInput = (EditText) findViewById(R.id.meanedit);

        mWordInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction()==KeyEvent.ACTION_DOWN) && keyCode==KeyEvent.KEYCODE_ENTER) {
                    mMeanInput.requestFocus();
                    return true;
                }
                return false;
            }
        });

        mMeanInput.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keycode, KeyEvent event) {
                String word = mWordInput.getText().toString();
                String mean = mMeanInput.getText().toString();

                if((event.getAction() == ACTION_DOWN) && (keycode == KeyEvent.KEYCODE_ENTER)) {

                    if(word.length() <= 0 || mean.length() <= 0){
                        Toast.makeText(getApplicationContext(), "다시 입력해주세요.",Toast.LENGTH_LONG).show();
                    }
                    else{
                        WordEntry entry = new WordEntry(word,mean);
                        a.addWord(entry);
                        Toast.makeText(getApplicationContext(), "단어가 추가되었습니다!!",Toast.LENGTH_LONG).show();
                        mWordInput.setText(null);
                        mMeanInput.setText(null);
                    }

                    return true;
                }
                return false;
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
                    a.addWord(entry);
                    Toast.makeText(getApplicationContext(), "단어가 추가되었습니다!!",Toast.LENGTH_LONG).show();
                    mWordInput.setText(null);
                    mMeanInput.setText(null);
                }

            }
        });


        //단어장모드
        btn = (Button)findViewById(R.id.OpenList);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,WordListActivity.class);
                startActivity(intent);
            }
        });

        //카메라와 Crop기능 구현
        btn = (Button)findViewById(R.id.OpenCrop);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CropActivity.class);
                startActivity(intent);
            }
        });



        //퀴즈모드
        btn = (Button)findViewById(R.id.OpenQuiz);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list = a.getAllWords();
                if (list.size() < 10) {
                    Toast.makeText(getApplicationContext(), "10개 이상의 단어를 등록해주세요", Toast.LENGTH_SHORT).show();
                }                                           // 등록된 단어가 없음

                else {
                    Toast.makeText(getApplicationContext(),"현재 " + list.size() + "개의 저장된 단어 중 10개를 랜덤으로 추출하여 단어 퀴즈를 시작합니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

}