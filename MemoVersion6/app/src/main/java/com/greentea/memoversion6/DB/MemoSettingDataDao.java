package com.greentea.memoversion6.DB;

import com.greentea.memoversion6.DB.data.MemoSettingData;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MemoSettingDataDao {

    @Query("SELECT * FROM app_setting_table")
    MemoSettingData getSettingData();

    @Insert(onConflict = REPLACE)
    void insertMemoSettingData(MemoSettingData memoSettingData);

    @Update
    void updateMemoSettingData(MemoSettingData memoSettingData);
}
