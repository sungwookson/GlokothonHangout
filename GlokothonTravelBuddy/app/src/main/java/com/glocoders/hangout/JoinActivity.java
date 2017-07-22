package com.glocoders.hangout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.glocoders.hangout.database.FirebaseHelper;

import java.io.ByteArrayOutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import com.glocoders.hangout.database.FirebaseHelper;


public class JoinActivity extends AppCompatActivity {

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

//                    bImgLink = getByteArrayFromBitmap(resized);
//                    사진을 바이트 어레이로 바꿔서 저장함

                    image_picture.setVisibility(View.VISIBLE);
                    // scroll 범위 늘리기


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        //Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data = stream.toByteArray();

        return data;
    }

}
