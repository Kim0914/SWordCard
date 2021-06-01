package com.example.swordcard;

import java.util.Date;

public class WordEntry {
    private String english = "";
    private String mean = "";
    private Date date;
    public WordEntry(String english,String mean){
        this.english = english;
        this.mean = mean;
    }

    @Override
    public String toString() {
        return "WordEntry{" +
                "english='" + english + '\'' +
                ", mean='" + mean + '\'' +
                ", date=" + date +
                '}';
    }

    public String get_mean() {return this.mean;}
    public String get_eng() {return this.english;}
}
