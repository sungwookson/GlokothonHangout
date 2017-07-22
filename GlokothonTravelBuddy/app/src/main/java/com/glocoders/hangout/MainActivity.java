package com.glocoders.hangout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.glocoders.hangout.database.FirebaseHelper;

public class MainActivity extends AppCompatActivity {

    FirebaseHelper fbHelper = new FirebaseHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sign_in = (Button) findViewById(R.id.sign_in);
        Button sign_up = (Button) findViewById(R.id.sign_up);

//        FirebaseApp.initializeApp(this);
//        fbHelper.initUserAuth();

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChoicePlaceActivity.class);
                startActivity(intent);
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });
    }
}
