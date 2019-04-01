package com.greentea.memoversion6.ViewModel;

import android.util.Log;

import com.greentea.memoversion6.DB.MemoRepositoryDB;
import com.greentea.memoversion6.DB.data.MemoData;
import com.greentea.memoversion6.DB.data.MemoSettingData;
//import com.greentea.memoversion6.DB.data.MemoSettingData;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MemoViewModel extends ViewModel {
    private LiveData<List<MemoData>> memoDataLiveData;
    private MemoRepositoryDB memoRepositoryDB;

    @Inject
    public MemoViewModel(MemoRepositoryDB memoRepositoryDB){
        Log.d("testViewModel", "make it !");
        this.memoRepositoryDB = memoRepositoryDB;
        init();
    }

    public void init(){
        if(this.memoDataLiveData != null){
            return;
        }
        memoDataLiveData = memoRepositoryDB.getMemoDataList();
    }
    public LiveData<List<MemoData>> getMemoDataLiveData(){
        return this.memoDataLiveData;
    }
    public void deleteMemoData(MemoData memoData){
        memoRepositoryDB.deleteMemo(memoData);
    }
    public void insertMemoData(MemoData memoData){
        memoRepositoryDB.insertMemo(memoData);
    }

    public void insertMemoSettingData(MemoSettingData memoSettingData) {memoRepositoryDB.insertSettingData(memoSettingData);}
}