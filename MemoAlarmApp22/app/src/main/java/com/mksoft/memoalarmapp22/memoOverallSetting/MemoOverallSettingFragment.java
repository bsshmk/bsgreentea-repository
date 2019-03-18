package com.mksoft.memoalarmapp22.memoOverallSetting;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mksoft.memoalarmapp22.MainActivity;
import com.mksoft.memoalarmapp22.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MemoOverallSettingFragment extends Fragment {

    MainActivity mainActivity;

    Button backBtn;
    NumberPicker np1, np2;

    RelativeLayout relativeLayout;

    Bundle bundle;

    int startTime, endTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.memo_overall_setting, container, false);
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

    public void init(ViewGroup viewGroup){

        relativeLayout = (RelativeLayout) viewGroup.findViewById(R.id.settingLayout2);
        backBtn = (Button) viewGroup.findViewById(R.id.settingPageBackButton);

        np1 = (NumberPicker) viewGroup.findViewById(R.id.np1);
        np2 = (NumberPicker) viewGroup.findViewById(R.id.np2);

        np1.setMinValue(0);
        np1.setMaxValue(23);
        np1.setValue(23);

        np2.setMinValue(0);
        np2.setMaxValue(23);
        np2.setValue(9);
    }

    public void saveData(){
        startTime = np1.getValue();
        endTime = np2.getValue();

        bundle = new Bundle();
        bundle.putInt("startTime", startTime);
        bundle.putInt("endTime", endTime);

        Toast.makeText(getContext(), "updated!", Toast.LENGTH_SHORT).show();

        mainActivity.OnFragmentChange(0,bundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    public void changeFragment(int idx, ){
//        mainActivity.OnFragmentChange(0, null);//이 페이지에서 데이터 처리하고 널을 넘기자.
//    }

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

}
