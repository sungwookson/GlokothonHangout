package com.glocoders.hangout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class PeopleListActivity extends AppCompatActivity {
    public AQuery aquery;
    public static String REQUEST_ADDRESS = "http://10.10.10.201:8080/promise/:id"; // + UID
    //WIFI 4
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_list);

        Calendar src = Calendar.getInstance();
        Calendar dst = Calendar.getInstance();

        src.set(Calendar.HOUR, 18);
        dst.set(Calendar.HOUR, 23);

        final ListView travelerList = (ListView) findViewById(R.id.traveler_list);
        final ArrayList<Traveler> travelers = new ArrayList<Traveler>();
        aquery = new AQuery(this);
        REQUEST_ADDRESS = REQUEST_ADDRESS.replace(":id","2");

        aquery.ajax(REQUEST_ADDRESS, JSONArray.class, new AjaxCallback<JSONArray>(){
            @Override
            public void callback(String url, JSONArray object, AjaxStatus status) {

                for(int i=0;i<object.length();i++){
                    try {
                        System.out.println(i);
                        JSONObject people = object.getJSONObject(i);
                        Traveler traveler = new Traveler(people.getString("nickname"),
                                     people.getString("plan") ,
                                     Double.parseDouble(people.getString("distance")),
                                     Integer.parseInt(people.getString("age")));
                        travelers.add(traveler);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    TravelerListAdapter adapter = new TravelerListAdapter(getApplicationContext(), travelers, R.layout.people_list_low);
                    travelerList.setAdapter(adapter);
                }
            }
        });




    }
}

