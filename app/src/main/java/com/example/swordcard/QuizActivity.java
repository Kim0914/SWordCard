package com.example.swordcard;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends MainActivity {
    Button btn_enter;
    Button btn_Exit;
    String answer;
    String word;
    EditText editText;
    TextView KorWord;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        KorWord = findViewById(R.id.korWord);
        editText = findViewById(R.id.edit_answer);
        btn_Exit = (Button)findViewById(R.id.btn_Exit);
        btn_enter = (Button)findViewById(R.id.btn_Enter);

        btn_enter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                answer = editText.getText().toString();
                if(answer.equals(list.get(0).get_eng())){
                    Toast.makeText(getApplicationContext(),"정답입니다!!",Toast.LENGTH_LONG).show();
                }

                else{
                    Toast.makeText(getApplicationContext(),"틀렸습니다. 다시 시도하세요!",Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_Exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        test();

    }

    public void test(){
        word = list.get(0).get_mean();
        KorWord.setText(word);
    }

}
