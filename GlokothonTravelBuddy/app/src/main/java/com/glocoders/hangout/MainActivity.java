package com.glocoders.hangout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.glocoders.hangout.database.FirebaseHelper;
import com.glocoders.hangout.database.UserInfoHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseHelper fbHelper;
    UserInfoHelper loginHelper;
    EditText edit_id;
    EditText edit_pwd;
    int is_auto = 0;
    CheckBox auto_box;
    private static FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DB
        FirebaseApp.initializeApp(this);
        fbHelper = new FirebaseHelper();
        fbHelper.initUserAuth();
        fbHelper.setAuthListener();
        loginHelper = new UserInfoHelper(getApplicationContext(), "asdf.db", 1);

        // if login data was in there && is_auto == 1 --> auto_login
        List<String> login = loginHelper.readLogin();
        if(login.size() == 3) {
            signInAccount(login.get(0), login.get(1));
        }

        /* Sign in, Sign up */
        Button sign_in = (Button) findViewById(R.id.sign_in);
        Button sign_up = (Button) findViewById(R.id.sign_up);
        auto_box = (CheckBox) findViewById(R.id.is_auto_login);

        /* ID, PW */
        edit_id = (EditText) findViewById(R.id.sign_in_id);
        edit_pwd = (EditText) findViewById(R.id.sign_in_pw);

        //PW
        edit_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edit_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());

        //if auto_login is true
        if(auto_box.isChecked()) {
            is_auto = 1;
        }

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ChoicePlaceActivity.class);
                startActivity(intent);
//                loginHelper.loginSetting(edit_id.getText().toString(), edit_pwd.getText().toString(), is_auto);
//                signInAccount(edit_id.getText().toString(), edit_pwd.getText().toString());
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

    private void signInAccount(String email, String password) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful() == false) {

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
                            alertDialogBuilder.show();
                        }else{
                            Intent intent = new Intent(getApplicationContext(), ChoicePlaceActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }


}


