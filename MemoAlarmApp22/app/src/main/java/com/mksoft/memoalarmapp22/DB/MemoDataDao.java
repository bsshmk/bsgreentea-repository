package com.mksoft.memoalarmapp22.DB;



import com.mksoft.memoalarmapp22.data.MemoData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface MemoDataDao {

    @Query("SELECT * FROM memo_data_table")
    List<MemoData> getAll();

    @Insert(onConflict = REPLACE)
    void insertMemo(MemoData memoData);

    @Query("DELETE from memo_data_table")
    void deleteAll();

    @Delete
    void delete(MemoData memoData);

}
