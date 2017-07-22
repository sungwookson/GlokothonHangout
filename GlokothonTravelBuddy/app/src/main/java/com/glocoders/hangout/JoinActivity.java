package com.glocoders.hangout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.glocoders.hangout.database.FirebaseHelper;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class JoinActivity extends AppCompatActivity {
    FirebaseHelper fbHelper = new FirebaseHelper();

    EditText edit_id;
    EditText edit_pw;
    EditText edit_nickname;
    EditText edit_realname;
    EditText edit_age;
    EditText edit_self_introduce;
    Button btn_image;
    Button btn_sign_up;

    String str_id;
    String str_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        edit_id = (EditText) findViewById(R.id.sign_up_id);
        edit_pw = (EditText) findViewById(R.id.sign_up_pw);
        edit_nickname = (EditText) findViewById(R.id.sign_up_nickname);
        edit_realname = (EditText) findViewById(R.id.sign_up_realname);
        edit_age = (EditText) findViewById(R.id.sign_up_age);
        edit_self_introduce = (EditText) findViewById(R.id.sign_up_self_introduce);
        btn_image = (Button) findViewById(R.id.select_profile_image);
        btn_sign_up = (Button) findViewById(R.id.sign_up_complete);

        setListener();
    }

    private void setListener() {
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_id = edit_id.getText().toString();
                str_pw = edit_pw.getText().toString();
                createUser();
            }
        });
    }

    private void createUser() {
        fbHelper.createAccount(str_id, str_pw);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    // Create URL
                    URL UserInfoEndpoint = new URL("https://10.10.10.201:8080/user/info");

                    // Create connection
                    HttpsURLConnection myConnection = (HttpsURLConnection) UserInfoEndpoint.openConnection();
                    myConnection.setRequestMethod("POST");

                    String req_uid = "uid="+fbHelper.getCurrentAuth().getCurrentUser().getUid();
                    String req_email = "email="+fbHelper.getCurrentAuth().getCurrentUser().getEmail();
                    String req_nickname = "email="+fbHelper.getCurrentAuth().getCurrentUser().getDisplayName();
                    String req_age = "age=22";
                    String req_detail = "detail=asdf";
                    String req_profile = "sampleFile=qq";

                    myConnection.setDoOutput(true);
//                    myConnection.getOutputStream().write();
                    if (myConnection.getResponseCode() == 200){

                    }else{

                    }
                }catch (Exception e){

                }
            }
        });
        this.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
