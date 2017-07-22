package com.glocoders.hangout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by dsm2016 on 2017-07-22.
 */

public class TravelerListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<Traveler> travelerList;
    int layout;

    public TravelerListAdapter(Context context, ArrayList<Traveler> travelerList, int layout) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.travelerList = travelerList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return travelerList.size();
    }

    @Override
    public Object getItem(int position) {
        return travelerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = inflater.inflate(layout, parent, false);

        TextView user_info = (TextView) convertView.findViewById(R.id.user_info);
        TextView distance = (TextView) convertView.findViewById(R.id.distance);
        TextView period = (TextView) convertView.findViewById(R.id.period);
        TextView activity = (TextView) convertView.findViewById(R.id.play_activity);

        Traveler traveler = travelerList.get(position);

        user_info.setText(traveler.getName() + " / " + Integer.toString(traveler.getAge()));
        distance.setText(traveler.getDistance().toString() + "KM");

        String src_ap = traveler.getSrc().get(Calendar.HOUR) < 12 ? "오후" : "오전";
        String dst_ap = traveler.getDst().get(Calendar.HOUR) < 12 ? "오후" : "오전";

        period.setText(src_ap + Integer.toString(traveler.getSrc().get(Calendar.HOUR)) + " ~ " +
                       dst_ap + Integer.toString(traveler.getDst().get(Calendar.HOUR)));

        activity.setText(traveler.getActivity());


        return convertView;
    }
}
