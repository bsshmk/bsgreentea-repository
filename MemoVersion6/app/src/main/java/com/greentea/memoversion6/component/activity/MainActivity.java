package com.greentea.memoversion6.component.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.greentea.memoversion6.HideKeyboard;
import com.greentea.memoversion6.R;
import com.greentea.memoversion6.component.activity.fragment.memoAdd.MemoAddFragment;
import com.greentea.memoversion6.component.activity.fragment.memoBody.MemoBodyFragment;
import com.greentea.memoversion6.component.activity.fragment.memoOverallSetting.MemoOverallSettingFragment;
import com.greentea.memoversion6.component.activity.fragment.memoTimeSetting.MemoTimeSettingFragment;
import com.greentea.memoversion6.component.service.Alarm.Service.AlarmService;

import java.util.Stack;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    MemoBodyFragment memoBodyFragment;
    MemoAddFragment memoAddFragment;
    MemoTimeSettingFragment memoTimeSettingFragment;//플레그먼트 주입이 될까?
    MemoOverallSettingFragment memoOverallSettingFragment;
    HideKeyboard hideKeyboard;
    FragmentTransaction fragmentTransaction;
    public static MainActivity mainActivity;

    BackPressCloseHandler backPressCloseHandler;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startAlarmService();
        backPressCloseHandler = new BackPressCloseHandler(this);
        mainActivity = this;
        init();
        this.configureDagger();
    }
    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
    private void configureDagger(){
        AndroidInjection.inject(this);
    }

    private void init(){
        hideKeyboard = new HideKeyboard(this);
        memoBodyFragment = new MemoBodyFragment();
        memoAddFragment = new MemoAddFragment();
        memoTimeSettingFragment = new MemoTimeSettingFragment();
        memoOverallSettingFragment = new MemoOverallSettingFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.mainContainer, memoBodyFragment, null).commit();

//        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, memoBodyFragment).commit();
    }
    public void OnFragmentChange(int idx, Bundle bundle, FragmentTransaction fragmentTransaction){
        if(idx == 0){
//            getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            fragmentTransaction.replace(R.id.mainContainer, memoBodyFragment, null);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//            fragmentTransaction.replace(R.id.mainContainer, memoBodyFragment).commit();
        }else if(idx == 1){
//            fragmentTransaction.replace(R.id.mainContainer, memoAddFragment, null);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//            fragmentTransaction.replace(R.id.mainContainer, memoAddFragment).commit();
        }else if(idx == 2){
//            memoTimeSettingFragment.setArguments(bundle);
//            fragmentTransaction.replace(R.id.mainContainer, memoTimeSettingFragment, null);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//            fragmentTransaction.replace(R.id.mainContainer, memoTimeSettingFragment).commit();
        }else if(idx == 3){
//            fragmentTransaction.replace(R.id.mainContainer, memoOverallSettingFragment, null);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//            fragmentTransaction.replace(R.id.mainContainer, memoOverallSettingFragment).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, memoOverallSettingFragment).addToBackStack(null).commit();
        }
    }

    public void startAlarmService(){
        Intent intent = new Intent(this, AlarmService.class);
        startService(intent);
    }
    public HideKeyboard getHideKeyboard(){
        return hideKeyboard;
    }

    //////////////////////
    public interface onKeyBackPressedListener{
        void onBackKey();
    }
    private onKeyBackPressedListener mOnKeyBackPressedListener;
    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener){
        mOnKeyBackPressedListener = listener;
    }

    @Override
    public void onBackPressed() {
        if(mOnKeyBackPressedListener != null) {
            mOnKeyBackPressedListener.onBackKey();
        }else{
            if(getSupportFragmentManager().getBackStackEntryCount() == 0){
                backPressCloseHandler.onBackPressed();
            }
            else{
                super.onBackPressed();
            }
        }
    }
}
