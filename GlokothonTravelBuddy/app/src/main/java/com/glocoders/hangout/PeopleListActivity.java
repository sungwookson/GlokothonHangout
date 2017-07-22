package com.glocoders.hangout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

public class PeopleListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_list);

        Calendar src = Calendar.getInstance();
        Calendar dst = Calendar.getInstance();

        src.set(Calendar.HOUR, 18);
        dst.set(Calendar.HOUR, 23);

        ListView travelerList = (ListView) findViewById(R.id.traveler_list);
        ArrayList<Traveler> travelers = new ArrayList<Traveler>();

        Traveler a = new Traveler("정필성", "낮잠", 2.3, 13,  src, dst);
        Traveler b = new Traveler("김민종", "민종이형의 경제학 강의", 2.9, 14,  src, dst);
        Traveler c = new Traveler("윤태훈", "태훈이의 뻘짓 감상", 1.2, 15, src, dst);

        travelers.add(a);
        travelers.add(b);
        travelers.add(c);

        TravelerListAdapter adapter = new TravelerListAdapter(this, travelers, R.layout.people_list_low);
        travelerList.setAdapter(adapter);

    }
}

