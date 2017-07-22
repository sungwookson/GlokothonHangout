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
    UserInfoHelper userHelper;
    EditText edit_id;
    EditText edit_pwd;
    private static FirebaseAuth mAuth;
    static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DB
        FirebaseApp.initializeApp(this);
        fbHelper = new FirebaseHelper();
        fbHelper.initUserAuth();
        fbHelper.setAuthListener();

        userHelper = new UserInfoHelper(getApplicationContext(), "user_info.db", 1);
        user = new User(userHelper.select());

        auto_login();

        /* Sign in, Sign up */
        Button sign_in = (Button) findViewById(R.id.sign_in);
        Button sign_up = (Button) findViewById(R.id.sign_up);

        /* ID, PW */
        edit_id = (EditText) findViewById(R.id.sign_in_id);
        edit_pwd = (EditText) findViewById(R.id.sign_in_pw);

        //PW
        edit_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edit_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());


        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ChoicePlaceActivity.class);
                startActivity(intent);

                signInAccount(edit_id.getText().toString(), edit_pwd.getText().toString());
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

    private void auto_login() {
        if(user.is_auto == true) {
            signInAccount(user.email, user.passwd);
        }
    }
}

class User{
    String uid;
    String email;
    String passwd;
    String detail;
    String profile_image;
    int age;
    boolean is_auto;

    public User(List<String> user_info) {
        uid = user_info.get(0);
        email = user_info.get(1);
        passwd = user_info.get(2);
        detail = user_info.get(3);
        profile_image = user_info.get(4);
        age = Integer.parseInt(user_info.get(5));
        is_auto = (Integer.parseInt(user_info.get(6)) == 1) ? true : false;
    }
}

