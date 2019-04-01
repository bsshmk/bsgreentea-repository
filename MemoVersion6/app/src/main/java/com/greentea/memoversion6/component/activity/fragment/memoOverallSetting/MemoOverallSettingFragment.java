package com.greentea.memoversion6.component.activity.fragment.memoOverallSetting;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.greentea.memoversion6.DB.MemoRepositoryDB;
import com.greentea.memoversion6.DB.data.MemoSettingData;
import com.greentea.memoversion6.R;
import com.greentea.memoversion6.ViewModel.MemoViewModel;
import com.greentea.memoversion6.component.activity.MainActivity;
import com.greentea.memoversion6.component.activity.fragment.memoBody.MemoBodyFragment;

import org.mozilla.javascript.tools.jsc.Main;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.AndroidSupportInjection;

public class MemoOverallSettingFragment extends Fragment implements MainActivity.onKeyBackPressedListener{

    MainActivity mainActivity;
    Button backBtn;
    NumberPicker np1, np2;
    RelativeLayout relativeLayout;
    MemoSettingData memoSettingData;

    FragmentTransaction fragmentTransaction;

    int nowStartTime, nowEndTime;

    @Inject
    MemoRepositoryDB memoRepositoryDB;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.memo_overall_setting, container, false);

        this.configureDagger();
        init(rootView);

        hideKeyboard();
        clickHideKeyboard();

        // save and go back
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        return rootView;
    }

    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }

    public void init(ViewGroup viewGroup){

        if(memoRepositoryDB.getSettingData() == null){
            memoSettingData = new MemoSettingData();
            memoSettingData.setSleepStartTime(0);
            memoSettingData.setSleepEndTime(0);
            memoRepositoryDB.insertSettingData(memoSettingData);
        }
        else {
            memoSettingData = memoRepositoryDB.getSettingData();
        }

        mainActivity = (MainActivity) getActivity();

        relativeLayout = (RelativeLayout) viewGroup.findViewById(R.id.settingLayout2);
        backBtn = (Button) viewGroup.findViewById(R.id.settingPageBackButton);

        np1 = (NumberPicker) viewGroup.findViewById(R.id.np1);
        np2 = (NumberPicker) viewGroup.findViewById(R.id.np2);

        nowStartTime = memoSettingData.getSleepStartTime();
        nowEndTime = memoSettingData.getSleepEndTime();

        np1.setMinValue(0);
        np1.setMaxValue(23);

        np2.setMinValue(0);
        np2.setMaxValue(23);

        np1.setValue(nowStartTime);
        np2.setValue(nowEndTime);
    }

    public void saveData(){

        memoSettingData.setSleepStartTime(np1.getValue());
        memoSettingData.setSleepEndTime(np2.getValue());

        memoRepositoryDB.insertSettingData(memoSettingData);

        Toast.makeText(getContext(), "updated!", Toast.LENGTH_SHORT).show();

        onBackKey();

//        changeFragment(0);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).setOnKeyBackPressedListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void changeFragment(int idx){
        mainActivity.OnFragmentChange(idx, null, getFragmentManager().beginTransaction());
    }

    private void hideKeyboard(){
        mainActivity.getHideKeyboard().hideKeyboard();
    }
    private void clickHideKeyboard(){
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
            }
        });
    }

    @Override
    public void onBackKey() {
        mainActivity = (MainActivity) getActivity();
        mainActivity.setOnKeyBackPressedListener(null);
        mainActivity.onBackPressed();
    }
}
