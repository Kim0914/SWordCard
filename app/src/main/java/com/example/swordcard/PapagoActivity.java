package com.example.swordcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PapagoActivity extends MainActivity{

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_papago);

        textView = (TextView)findViewById(R.id.transTextView);
        Translate papago = new Translate();
        papago.execute();
    }

    class Translate extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String clientId = "JB4GBnqHLvXYR8iCZx4m";//애플리케이션 클라이언트 아이디값";
            String clientSecret = "9pGNBZwKjs";//애플리케이션 클라이언트 시크릿값";
            try {
                String text = URLEncoder.encode("안녕하세요. 오늘 날씨가 참 좋네요.", "UTF-8");
                String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                // post request
                String postParams = "source=ko&target=en&text=" + text;
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(postParams);
                wr.flush();
                wr.close();
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if(responseCode==200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
                String s = response.toString();
                s = s.split("\"")[27];
                //textView.setText(response.toString());
                textView.setText(s);
            } catch (Exception e) {
                System.out.println(e);
            }
            return null;
        }
    }

}
