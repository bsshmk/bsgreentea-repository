package com.mksoft.memoalarmapp22.DB;

import android.content.Context;

import com.mksoft.memoalarmapp22.data.MemoData;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MemoData.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract MemoDataDao memoDataDao();
    private static volatile AppDB INSTANCE;//volatile 메모리에 접근 가능

    static AppDB getDatabase(final Context context){
        if(INSTANCE == null){

            synchronized (AppDB.class){
                //아래의 방법은 콜백을 부르고 원하는 초기에 원하는 행동을 뒤로 넘긴다.addCallback(roomDatabaseCallback) 콜백을 불러서 원하는 행동을
                //에이싱크에서 돌려준다.
                /*
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                           AppDB.class, "testDB").fallbackToDestructiveMigration().addCallback(roomDatabaseCallback).build();
                */
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                           AppDB.class, "test_db").fallbackToDestructiveMigration().build();

            }

        }

        return INSTANCE;
    }
}
