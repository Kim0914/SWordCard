package com.example.swordcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    List<WordEntry> list = new Vector<WordEntry>();
    Button btn;
    private Button btn_enter;
    private EditText mWordInput;
    private EditText mMeanInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WordModule a = new WordModule(MainActivity.this);
        list = a.getAllWords(); // db에서 단어 데이터를 받아온다.
        mWordInput = (EditText) findViewById(R.id.wordedit);
        mMeanInput = (EditText) findViewById(R.id.meanedit);

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
                }


                Intent intent= new Intent(MainActivity.this,QuizActivity.class);
            }
        });

        
        //촬영모드
        btn = (Button)findViewById(R.id.OpenCam);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CameraActivity.class);
                startActivity(intent);
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

        //단어장모드
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