package com.greentea.memoversion6.DB;

import android.os.AsyncTask;

import com.greentea.memoversion6.DB.data.MemoData;
import com.greentea.memoversion6.DB.data.MemoSettingData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;

@Singleton
public class MemoRepositoryDB {
    private MemoDataDao memoDataDao;
    private LiveData<List<MemoData>> memoDataList;
    private List<MemoData> staticMemoDataList;

    private MemoSettingDataDao memoSettingDataDao;
    private MemoSettingData memoSettingData;

    @Inject
    public MemoRepositoryDB(MemoDataDao memoDataDao, MemoSettingDataDao memoSettingDataDao) {
        this.memoDataDao = memoDataDao;
        memoDataList = memoDataDao.getAll();

        this.memoSettingDataDao = memoSettingDataDao;
    }

    public LiveData<List<MemoData>> getMemoDataList(){
        return memoDataList;
    }
    public List<MemoData> getStaticMemoDataList(){
        try {
            staticMemoDataList = new getAllAsyncTask(memoDataDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return staticMemoDataList;
    }

    public MemoSettingData getSettingData(){
        try {
            memoSettingData = new getSettingAsyncTask(memoSettingDataDao).execute().get();
        } catch (ExecutionException e){
            e.printStackTrace();
        } catch (InterruptedException  e){
            e.printStackTrace();
        }
        return memoSettingData;
    }

    public static class getSettingAsyncTask extends AsyncTask<Void, Void, MemoSettingData>{
        private MemoSettingDataDao asyncSettingDao;
        getSettingAsyncTask(MemoSettingDataDao dao){asyncSettingDao = dao;}
        @Override
        protected MemoSettingData doInBackground(Void... voids){
            return asyncSettingDao.getSettingData();
        }
    }

    private static class getAllAsyncTask extends AsyncTask<Void, Void, List<MemoData>>{
        private MemoDataDao asyncUserDao;
        getAllAsyncTask(MemoDataDao dao){
            asyncUserDao = dao;
        }

        @Override
        protected List<MemoData> doInBackground(Void... voids) {
            return asyncUserDao.getStaticAll();
        }
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

    public void insertSettingData(MemoSettingData memoSettingData){
        new insertSettingAsyncTask(memoSettingDataDao).execute(memoSettingData);
    }

    private static class insertSettingAsyncTask extends AsyncTask<MemoSettingData, Void, Void>{
        private MemoSettingDataDao asyncSettingDao;
        insertSettingAsyncTask(MemoSettingDataDao dao){asyncSettingDao = dao;}

        @Override
        protected Void doInBackground(MemoSettingData... memoSettingData){
            asyncSettingDao.insertMemoSettingData(memoSettingData[0]);
            return null;
        }
    }

    public void updateSettingDate(MemoSettingData memoSettingData){
        new updateSettingAsyncTask(memoSettingDataDao).execute(memoSettingData);
    }

    private static class updateSettingAsyncTask extends AsyncTask<MemoSettingData, Void, Void>{
        private MemoSettingDataDao asyncSettingDao;
        updateSettingAsyncTask(MemoSettingDataDao memoSettingDataDao){asyncSettingDao = memoSettingDataDao;}

        @Override
        protected Void doInBackground(MemoSettingData... memoSettingData){
            asyncSettingDao.updateMemoSettingData(memoSettingData[0]);
            return null;
        }
    }
}