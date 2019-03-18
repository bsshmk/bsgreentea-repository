package com.mksoft.memoalarmapp22.DB;

import android.app.Application;
import android.os.AsyncTask;


import com.mksoft.memoalarmapp22.data.MemoData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ReposityDB {
    private MemoDataDao memoDataDao;
    private List<MemoData> memoDataList;

    public ReposityDB(Application application){
        AppDB appDB = AppDB.getDatabase(application);
        this.memoDataDao = appDB.memoDataDao();

    }
    public List<MemoData> getMemoDataList(){

        try {
            memoDataList = new getAllAsyncTask(memoDataDao).execute().get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return memoDataList;
    }

    public void insertMemo(MemoData memoData){
        new insertAsyncTask(memoDataDao).execute(memoData);
    }

    public void deleteMemo(MemoData memoData){
        new deleteAsyncTask(memoDataDao).execute(memoData);
    }

    private static class deleteAsyncTask extends AsyncTask<MemoData, Void, Void>{
        private MemoDataDao asyncUserDao;
        deleteAsyncTask(MemoDataDao dao){
            asyncUserDao = dao;
        }

        @Override
        protected Void doInBackground(MemoData... memoData) {
            asyncUserDao.delete(memoData[0]);
            return null;
        }
    }
    private static class insertAsyncTask extends AsyncTask<MemoData, Void, Void>{
        private MemoDataDao asyncUserDao;
        insertAsyncTask(MemoDataDao dao){
            asyncUserDao = dao;
        }

        @Override
        protected Void doInBackground(MemoData... memoData) {
            asyncUserDao.insertMemo(memoData[0]);
            return null;
        }
    }
    private static class getAllAsyncTask extends AsyncTask<Void, Void, List<MemoData>>{
        private MemoDataDao asyncUserDao;

        getAllAsyncTask(MemoDataDao dao){
            asyncUserDao = dao;
        }

        @Override
        protected List<MemoData> doInBackground(Void... voids) {

            return asyncUserDao.getAll();
        }
    }
}