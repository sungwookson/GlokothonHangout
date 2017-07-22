package com.glocoders.hangout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        EditText edit_id = (EditText) findViewById(R.id.sign_up_id);
        EditText edit_pw = (EditText) findViewById(R.id.sign_up_pw);
        EditText edit_nickname = (EditText) findViewById(R.id.sign_up_nickname);
        EditText edit_realname = (EditText) findViewById(R.id.sign_up_realname);
        EditText edit_age = (EditText) findViewById(R.id.sign_up_age);
        EditText edit_self_introduce = (EditText) findViewById(R.id.sign_up_self_introduce);
        Button btn_image = (Button) findViewById(R.id.select_profile_image);
        Button btn_sign_up = (Button) findViewById(R.id.sign_up_complete);
    }
}
