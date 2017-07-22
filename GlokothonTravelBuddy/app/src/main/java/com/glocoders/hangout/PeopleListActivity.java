package com.glocoders.hangout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PeopleListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.people_recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

        List<Traveler> items = new ArrayList<>();
        Traveler[] item = new Traveler[3];

        Calendar src = Calendar.getInstance();
        Calendar dst = Calendar.getInstance();

        src.set(Calendar.HOUR, 16);
        dst.set(Calendar.HOUR, 23);

        item[0] = new Traveler("정필성", "같이 밥먹을 사람구해요!", 2.3, src, dst);
        item[1] = new Traveler("김종민", "같이 밥먹을 사람구해요!", 2.5, src, dst);
        item[2] = new Traveler("이상아", "같이 밥먹을 사람구해요!", 2.8, src, dst);

        for(int i=0; i<3; i++) items.add(item[i]);

//        recyclerView.setAdapter(new PeopleRecyclerAdapter(getApplicationContext(), items, R.layout.activity_people_list) );
    }
}

