package com.example.swordcard;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

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
    int RC_SIGN_IN = 9002;
    GoogleSignInClient mGoogleSignInClient;
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
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
//                finish();
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
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("on Activity Result...");

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        System.out.println("HANDLE SIGN IN RESULT!");
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            loginAlert("로그인 완료",account.getDisplayName()+"의 단어장을 가져옵니다.", "ID"+account.getId());
//            System.out.println("ID:");
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            System.out.println("HANDLE SIGN EXCEPTION...");
            System.out.println( "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
        }
    }

    private void loginAlert(String title, String message, String ID)
    {
//        WordModule
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("확인", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        wc.upload(ID);
                        wc.download(ID);
                        dialog.dismiss();
                        finish();
                    }
                })
                .show();
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