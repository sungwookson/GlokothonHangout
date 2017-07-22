package com.glocoders.hangout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.glocoders.hangout.database.FirebaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;


public class JoinActivity extends AppCompatActivity {
    private static FirebaseAuth mAuth;

    // DB
    FirebaseHelper fbHelper = new FirebaseHelper();

    // 레이아웃
    LinearLayout layout_parent;
    LinearLayout layout_inner;

    // 입력 창
    EditText edit_id;
    EditText edit_pw;
    EditText edit_nickname;
    EditText edit_realname;
    EditText edit_age;
    EditText edit_self_introduce;

    // 사진
    ImageView image_picture;

    // 버튼
    Button btn_image;
    Button btn_sign_up;

    String str_id;
    String str_pw;

    // 사진
    int REQ_CODE_SELECT_IMAGE = 100;
    Uri photoUri;
    int RESIZE_WIDTH = 250;
    int RESIZE_HEIGHT = 250;
    String profile_image;

    // REST
    String url = "http://10.10.10.201:8080/user/info";
    AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //값 대입
        edit_id = (EditText) findViewById(R.id.sign_up_id);
        edit_pw = (EditText) findViewById(R.id.sign_up_pw);
        edit_nickname = (EditText) findViewById(R.id.sign_up_nickname);
        edit_realname = (EditText) findViewById(R.id.sign_up_realname);
        edit_age = (EditText) findViewById(R.id.sign_up_age);
        edit_self_introduce = (EditText) findViewById(R.id.sign_up_self_introduce);
        btn_image = (Button) findViewById(R.id.select_profile_image);
        btn_sign_up = (Button) findViewById(R.id.sign_up_complete);
        image_picture = (ImageView) findViewById(R.id.image_picture);

        setListener();
    }

    public void pushtoREST() {
        HashMap<String, String> params = new HashMap<String, String>();

        String email = edit_id.getText().toString();
//        uid = //
        String nickname = edit_nickname.getText().toString();
        String age = edit_age.getText().toString();
        String detail = edit_self_introduce.getText().toString();
        String uid = fbHelper.getCurrentAuth().getCurrentUser().getUid();

        params.put("email", email);
        params.put("uid", uid);
        params.put("nickname", nickname);
        params.put("age", age);
        params.put("detail", detail);
        params.put("sampleFile", profile_image);

        aq = new AQuery(getApplicationContext());
        aq.ajax(url, params, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String object, AjaxStatus status) {
                if (status.getCode() == 200) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "입력정보를 확인하세요", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void setListener() {
        // 회원 가입
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_id = edit_id.getText().toString();
                str_pw = edit_pw.getText().toString();
                createUser();
            }
        });

        // 사진 추가하기
        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
            }
        });
    }

    private void createUser() {
        createAccount(str_id, str_pw);

        this.finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_SELECT_IMAGE) {

            if (resultCode == Activity.RESULT_OK) {
                if(data==null){
                    return ;
                }

                try {
                    photoUri = data.getData();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;
                    Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(),photoUri);
                    Bitmap resized = Bitmap.createScaledBitmap(bm, RESIZE_WIDTH, RESIZE_HEIGHT, true);

                    //배치해놓은 ImageView에 set
                    image_picture.setImageBitmap(resized);

                    Toast.makeText(getApplicationContext(), "사진을 추가하였습니다", Toast.LENGTH_SHORT).show();

                    profile_image = getByteArrayFromBitmap(resized);
//                    사진을 바이트 어레이로 바꿔서 저장함

                    image_picture.setVisibility(View.VISIBLE);
                    // scroll 범위 늘리기


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



    // stream -> byte -> string(encoded)
    public String getByteArrayFromBitmap(Bitmap bitmap) {
        //Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data = stream.toByteArray();
        String encoded = Base64.encodeToString(data, Base64.DEFAULT);

        return encoded;
    }

    public void createAccount(String email, String password) {
        final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG CREATE", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.d("TAG CREATE", "A user created failed!");
                        } else {
                            FirebaseUser user = task.getResult().getUser();
                            databaseRef.child("users").child(user.getUid()).setValue(fbHelper.getUserInformation(user));
                            pushtoREST();
                        }
                    }
                });
    }

}
