package com.mksoft.memoalarmapp22.data;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "memo_data_table", primaryKeys = {"regist_date", "end_date"})
public class MemoData implements Serializable {
    @NonNull
    @ColumnInfo(name = "regist_date")
    private String registDateTextView;
    @ColumnInfo(name = "memo_text")
    private String memoText;

    @NonNull
    @ColumnInfo(name = "end_date")
    private String endDateTextView;

    @ColumnInfo(name = "min_time")
    private String minTime;

    @ColumnInfo(name = "memo_title")
    private String memoTitle;

    @ColumnInfo(name = "sleep_start_time")
    private String sleepStartTime;

    @ColumnInfo(name = "sleep_end_time")
    private String sleepEndTime;


    public MemoData(){}

    protected MemoData(android.os.Parcel in) {
        registDateTextView = in.readString();
        memoText = in.readString();
        endDateTextView = in.readString();
        minTime = in.readString();
    }


    public String getRegistDateTextView() {
        return registDateTextView;
    }

    public String getMemoText() {
        return memoText;
    }

    public String getEndDateTextView() {
        return endDateTextView;
    }

    public String getMinTime(){return minTime;}

    public void setRegistDateTextView(String registDateTextView) {
        this.registDateTextView = registDateTextView;
    }

    public void setMemoText(String memoText) {
        this.memoText = memoText;
    }

    public void setEndDateTextView(String endDateTextView) {
        this.endDateTextView = endDateTextView;
    }

    public void setMinTime(String minTime){
        this.minTime=minTime;
    }


    public String getSleepStartTime() {
        return sleepStartTime;
    }

    public void setSleepStartTime(String sleepStartTime) {
        this.sleepStartTime = sleepStartTime;
    }

    public String getSleepEndTime() {
        return sleepEndTime;
    }

    public void setSleepEndTime(String sleepEndTime) {
        this.sleepEndTime = sleepEndTime;
    }

    public String getMemoTitle() {
        return memoTitle;
    }

    public void setMemoTitle(String memoTitle) {
        this.memoTitle = memoTitle;
    }
}
