package com.greentea.memoversion6.DB.data;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "app_setting_table")
public class MemoSettingData implements Serializable {

    public MemoSettingData(){}

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id = 0;

    @ColumnInfo(name = "sleep_start")
    private int sleepStartTime;

    @ColumnInfo(name = "sleep_end")
    private int sleepEndTime;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSleepStartTime() {
        return sleepStartTime;
    }

    public void setSleepStartTime(int sleepStartTime) {
        this.sleepStartTime = sleepStartTime;
    }

    public int getSleepEndTime() {
        return sleepEndTime;
    }

    public void setSleepEndTime(int sleepEndTime) {
        this.sleepEndTime = sleepEndTime;
    }
}
