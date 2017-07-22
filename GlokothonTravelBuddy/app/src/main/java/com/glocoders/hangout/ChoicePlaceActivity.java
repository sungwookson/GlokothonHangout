package com.glocoders.hangout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class ChoicePlaceActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    Toolbar toolbar;

    EditText edit_where; //어디서
    Button btn_choice_date; // 언제 (버튼)
    CalendarView choice_date; // 언제 (달력)
    TextView show_date; //언제 (보여줌)
    Spinner spinner; // 무엇을
    ArrayList<String> playList = new ArrayList<String>(Arrays.asList("식사", "술", "카페, 디저트", "여행친구", "운동", "공부", "행아웃"));
    ArrayAdapter<String> adapter;

    String sendDateToRest;

    // REST
    String url = "http://10.10.10.201:8080/user/info";
    AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_place);

        edit_where = (EditText) findViewById(R.id.edit_where); // 어디서
//        edit_date = (EditText) findViewById(R.id.edit_date); // 언제
        btn_choice_date = (Button) findViewById(R.id.btn_choice_date); // 언제 (버튼)
        choice_date = (CalendarView) findViewById(R.id.choice_date); // 언제 (달력)
        choice_date.setVisibility(View.INVISIBLE);
        show_date = (TextView) findViewById(R.id.show_date); // 언제 (보여줌)

        initListener();
        initNavigation();
        initSpinner();

    }




    public void initSpinner(){
        spinner = (Spinner) findViewById(R.id.spinner_what); // 무엇을

        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, playList);
        spinner.setAdapter(adapter);
    }



    public void initListener() {
        /* 언제 */
        // 버튼
        btn_choice_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice_date.setVisibility(View.VISIBLE);
                Log.d("calendar","[BUTTON] clicked, calendar open!");

            }
        });

        // 캘린더
        choice_date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                String date = year+"년 "+month+"월 "+day+"일 ";
                show_date.setText(date);
                choice_date.setVisibility(View.INVISIBLE);
                Log.d("calendar","choiced!");

                sendDateToRest = year+"-";

                if(month < 10)
                    sendDateToRest = sendDateToRest + "0"+month+"-";
                else sendDateToRest = sendDateToRest +"-"+month;

                if(day < 10)
                    sendDateToRest = sendDateToRest + "0"+day;
                else sendDateToRest = sendDateToRest +"-"+day;

                sendDateToRest += " ";

                long now = System.currentTimeMillis();
                Date date2 = new Date(now);
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String strNow = sdfNow.format(date2);

                strNow = strNow.substring(strNow.indexOf(' ')+1);
                Log.d("date_print", strNow);

                String strTime[] = strNow.split(":");
                Log.d("date_print",strTime[0]+","+strTime[1]+","+strTime[2]);

                sendDateToRest += strTime[0]+"-"+strTime[1]+"-"+strTime[2];
                Log.d("date_print", sendDateToRest);


            }
        });


    }

    public void initNavigation() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.choice_place, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void pushtoREST() {
        HashMap<String, Object> params = new HashMap<>();

        //언제

        //어디서
        String where = edit_where.getText().toString();
        HashMap<String, Double> loc = new HashMap<>();
        loc.put("lon", 126.97);
        loc.put("lat", 37.56); //만약에 [서울]이 잘 안뜨면 lon, lat의 값을 바꿔보자
        //location - lon, lat

        //무엇을
        String what = spinner.getSelectedItem().toString();
        //category


        params.put("location", loc);
        params.put("category", what);
        params.put("date",sendDateToRest);

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


}