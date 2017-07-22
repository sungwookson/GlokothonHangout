package com.glocoders.hangout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.glocoders.hangout.database.FirebaseHelper;
import com.google.firebase.FirebaseApp;

import static android.R.id.input;

public class MainActivity extends AppCompatActivity {
    FirebaseHelper fbHelper;
    EditText edit_id;
    EditText edit_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DB
        FirebaseApp.initializeApp(this);
        fbHelper = new FirebaseHelper();
        fbHelper.initUserAuth();
        fbHelper.setAuthListener();

        /* Sign in, Sign up */
        Button sign_in = (Button) findViewById(R.id.sign_in);
        Button sign_up = (Button) findViewById(R.id.sign_up);

        /* ID, PW */
        edit_id = (EditText)findViewById(R.id.sign_in_id);
        edit_pwd = (EditText)findViewById(R.id.sign_in_pw);

        //PW
        edit_pwd.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        edit_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());


        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fbHelper.signInAccount(edit_id.getText().toString(), edit_pwd.getText().toString()) == 1){
                    Intent intent = new Intent(getApplicationContext(), ChoicePlaceActivity.class);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setTitle("로그인 실패");

                    alertDialogBuilder
                            .setMessage("이메일 혹은 비밀번호를 확인해주세요.")
                            .setCancelable(false)
                            .setPositiveButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fbHelper.removeAuthListener();
    }
}
