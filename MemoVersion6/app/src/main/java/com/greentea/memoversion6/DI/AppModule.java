package com.greentea.memoversion6.DI;

import android.app.Application;

import com.greentea.memoversion6.DB.AppDB;
import com.greentea.memoversion6.DB.MemoDataDao;
import com.greentea.memoversion6.DB.MemoRepositoryDB;
import com.greentea.memoversion6.DB.MemoSettingDataDao;
import com.greentea.memoversion6.DB.data.MemoSettingData;
import com.greentea.memoversion6.DB.MemoSettingDataDao;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module(includes = ViewModelModule.class)
public class AppModule {

    // --- DATABASE INJECTION ---
    @Provides
    @Singleton
    AppDB provideDatabase(Application application) {
        return Room.databaseBuilder(application,
                AppDB.class, "testDatabase.db")
                .build();
    }
    @Provides
    @Singleton
    MemoDataDao provideMemoDataDao(AppDB database) { return database.memoDataDao(); }

    @Provides
    @Singleton
    MemoSettingDataDao provideMemoSettingDataDao(AppDB database){ return database.memoSettingDataDao(); }

    // --- REPOSITORY INJECTION ---
    @Provides
    @Singleton
    MemoRepositoryDB provideMemoRepositoryDB(MemoDataDao memoDataDao, MemoSettingDataDao memoSettingDataDao) {
        return new MemoRepositoryDB(memoDataDao, memoSettingDataDao);
    }
}
