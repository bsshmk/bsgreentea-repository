package com.mksoft.memoalarmapp22;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.mksoft.memoalarmapp22.DB.ReposityDB;
import com.mksoft.memoalarmapp22.memoAdd.MemoAddFragment;
import com.mksoft.memoalarmapp22.memoBody.MemoBodyFragment;
import com.mksoft.memoalarmapp22.memoOverallSetting.MemoOverallSettingFragment;
import com.mksoft.memoalarmapp22.memoTimeSetting.MemoTimeSettingFragment;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    MemoBodyFragment memoBodyFragment;
    MemoAddFragment memoAddFragment;
    MemoTimeSettingFragment memoTimeSettingFragment;
    MemoOverallSettingFragment memoOverallSettingFragment;
    ReposityDB reposityDB;
    HideKeyboard hideKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init(){
        hideKeyboard = new HideKeyboard(this);
        reposityDB = new ReposityDB(getApplication());
        memoBodyFragment = new MemoBodyFragment();
        memoAddFragment = new MemoAddFragment();
        memoTimeSettingFragment = new MemoTimeSettingFragment();
        memoOverallSettingFragment = new MemoOverallSettingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, memoBodyFragment).commit();
    }
    public void OnFragmentChange(int idx, Bundle bundle){
        if(idx == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, memoBodyFragment).commit();
        }else if(idx == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, memoAddFragment).commit();
        }else if(idx == 2){
            memoTimeSettingFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, memoTimeSettingFragment).commit();
        }else if(idx == 3){
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, memoOverallSettingFragment).commit();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.action_setting:
//                Toast.makeText(this, "setting page", Toast.LENGTH_SHORT).show();
//                OnFragmentChange(3,null);
//                return true;
//            default:
//                    return super.onOptionsItemSelected(item);
//        }
//    }
    public HideKeyboard getHideKeyboard(){
        return hideKeyboard;
    }
    public ReposityDB getReposityDB() {
        return reposityDB;
    }
}
