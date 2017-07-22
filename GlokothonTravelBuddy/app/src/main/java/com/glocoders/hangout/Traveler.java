package com.glocoders.hangout;

import java.util.Calendar;

/**
 * Created by dsm2016 on 2017-07-22.
 */

// name : 이름, activity : 활동내용, distance : 거리, src ~ dst : 활동시간 src 부터 dst 까지
public class Traveler {
    private String name, activity;
    private Double distance;
    private Calendar src, dst;
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Traveler(String name, String activity, Double distance, int age, Calendar src, Calendar dst) {
        this.name = name;
        this.activity = activity;
        this.distance = distance;
        this.age = age;
        this.src = src;
        this.dst = dst;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Calendar getSrc() {
        return src;
    }

    public void setSrc(Calendar src) {
        this.src = src;
    }

    public Calendar getDst() {
        return dst;
    }

    public void setDst(Calendar dst) {
        this.dst = dst;
    }
}
