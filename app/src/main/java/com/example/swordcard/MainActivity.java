package com.example.swordcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    List<WordEntry> list = new Vector<WordEntry>();
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WordModule a = new WordModule(MainActivity.this);
        System.out.println("HELLO");
        a.removeWord("Car");
        System.out.println("Words:"+a.getAllWords());

        list = a.getAllWords(); // db에서 단어 데이터를 받아온다.

        
        //촬영모드
        btn = (Button)findViewById(R.id.OpenCam);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CameraActivity.class);
                startActivity(intent);
            }
        });

        // 번역확인
        btn = (Button)findViewById(R.id.OpenTranslate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PapagoActivity.class);
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



        //퀴즈모드
        btn = (Button)findViewById(R.id.OpenQuiz);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() == 0) {
                    Toast.makeText(getApplicationContext(), "단어장에 저장된 단어가 없습니다. 단어를 등록 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                }                                           // 등록된 단어가 없음

                else {
                    Toast.makeText(getApplicationContext(),"현재 " + list.size() + "개의 저장된 단어 중 랜덤으로 추출하여 단어 퀴즈를 시작합니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                    startActivity(intent);
                }
            }
        });


    }

}