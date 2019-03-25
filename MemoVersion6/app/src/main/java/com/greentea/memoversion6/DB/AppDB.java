package com.greentea.memoversion6.DB;

import com.greentea.memoversion6.DB.data.MemoData;
import com.greentea.memoversion6.DB.data.MemoSettingData;
import com.greentea.memoversion6.DB.data.MemoSettingData;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MemoData.class, MemoSettingData.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract MemoDataDao memoDataDao();
    public abstract MemoSettingDataDao memoSettingDataDao();
    private static volatile AppDB INSTANCE;//volatile 메모리에 접근 가능
}
