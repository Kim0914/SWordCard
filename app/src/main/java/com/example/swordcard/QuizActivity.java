package com.example.swordcard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import static android.view.KeyEvent.ACTION_DOWN;

public class QuizActivity extends MainActivity {
    Button btn_enter;
    Button btn_Exit;
    Button btn_Pass;
    String answer;
    EditText editText;
    TextView KorWord;

    int turn = 1;
    Random r;
    String s;
    int Rcnt = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        KorWord = findViewById(R.id.korWord);
        editText = findViewById(R.id.edit_answer);
        btn_Exit = findViewById(R.id.btn_Exit);
        btn_enter = findViewById(R.id.btn_Enter);
        btn_Pass = findViewById(R.id.btn_Pass);




        r = new Random();
        answer = editText.getText().toString();

        Collections.shuffle(list);

        newQuestion(turn);

        editText.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keycode, KeyEvent event) {
                if((event.getAction() == ACTION_DOWN) && (keycode == KeyEvent.KEYCODE_ENTER)) {

                    if(editText.getText().toString().equalsIgnoreCase(list.get(turn-1).get_eng())) {
                        Toast.makeText(getApplicationContext(), "정답입니다.", Toast.LENGTH_SHORT).show();
                        Rcnt ++;
                        editText.setText(null);


                        if (turn < 10) {
                            turn++;
                            newQuestion(turn);
                        }
                        else {
                            s =  Integer.toString(Rcnt);
                            Toast.makeText(getApplicationContext(), "퀴즈가 끝났습니다.\n 총 10문제 중 " + s + "개 정답", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "오답입니다. 다시 시도하세요.", Toast.LENGTH_SHORT).show();


                    }
                    return true;
                }
                return false;
            }
        });

        btn_enter.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {


                if(editText.getText().toString().equalsIgnoreCase(list.get(turn-1).get_eng())) {
                    Toast.makeText(getApplicationContext(), "정답입니다.", Toast.LENGTH_SHORT).show();
                    Rcnt++;
                    editText.setText(null);


                    if (turn < 10) {
                        turn++;
                        newQuestion(turn);
                    }
                    else {
                        s = Integer.toString(Rcnt);
                        Toast.makeText(getApplicationContext(), "퀴즈가 끝났습니다.\n 총 10문제 중 " + s + "개 정답", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "오답입니다. 다시 시도하세요.", Toast.LENGTH_SHORT).show();

                }


            }
        });

        btn_Exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_Pass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (turn < 10) {
                    turn++;
                    editText.setText(null);
                    newQuestion(turn);
                }
                else {

                    s =  Integer.toString(Rcnt);
                    Toast.makeText(getApplicationContext(), "퀴즈가 끝났습니다.\n 총 10문제 중 " + s + "개 정답", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });

    }

    public void newQuestion(int number) {
        KorWord.setText(list.get(number - 1).get_mean());
    }
}
