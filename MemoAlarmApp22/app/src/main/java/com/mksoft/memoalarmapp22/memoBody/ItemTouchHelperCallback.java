package com.mksoft.memoalarmapp22.memoBody;


import com.mksoft.memoalarmapp22.MainActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemTouchHelperCallback extends ItemTouchHelper.SimpleCallback {

    private MemoAdapter mAdapter;
    private MainActivity mainActivity;
    public ItemTouchHelperCallback(MemoAdapter adapter, MainActivity mainActivity) {
        super(0,  ItemTouchHelper.RIGHT);
        mAdapter = adapter;
        this.mainActivity = mainActivity;
    }


    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        mainActivity.getReposityDB().deleteMemo(mAdapter.getItem(position));
        mAdapter.deleteItem(position);

    }//스와이프 삭제를 위한 부분

}
//클릭시 수정페이지 만들어주기