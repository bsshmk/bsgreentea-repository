package com.greentea.memoversion6.component.activity.fragment.memoBody;


import android.util.Log;

import com.greentea.memoversion6.DB.MemoRepositoryDB;
import com.greentea.memoversion6.DB.data.MemoData;
import com.greentea.memoversion6.ViewModel.MemoViewModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemTouchHelperCallback extends ItemTouchHelper.SimpleCallback {

    private MemoAdapter mAdapter;
    private MemoRepositoryDB memoRepositoryDB;
    private MemoViewModel memoViewModel;
    public ItemTouchHelperCallback(MemoAdapter adapter, final MemoRepositoryDB memoRepositoryDB) {
        super(0,  ItemTouchHelper.RIGHT);
        mAdapter = adapter;
        this.memoRepositoryDB = memoRepositoryDB;
//        this.memoViewModel = memoViewModel;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        //MemoData temp = mAdapter.getItem(position);//어뎁터에서 삭제전에 미리 정보를 저장하고
        //mAdapter.deleteItem(position);
////        mAdapter.deleteItem(viewHolder.getAdapterPosition());
//        MemoData memoData = mAdapter.getMemoAt(position);
//        memoViewModel.deleteMemoData(mAdapter.getMemoAt(position));
        if(memoRepositoryDB == null)
            Log.d("testerror", "hi");
        memoRepositoryDB.deleteMemo(mAdapter.getItem(position));//디비에서 지우자.
//        mAdapter.deleteItem(position);
    }//스와이프 삭제를 위한 부분
}
//클릭시 수정페이지 만들어주기