package com.mksoft.memoalarmapp22.memoTimeSetting;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mksoft.memoalarmapp22.MainActivity;
import com.mksoft.memoalarmapp22.R;
import com.mksoft.memoalarmapp22.data.MemoData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MemoTimeSettingFragment extends Fragment {
    MemoData MD;
    String mTitle,mText;

    MainActivity mainActivity;
    Button saveButton;
    SimpleDateFormat mFormat;
    long mNow;
    Date mDate;
    String time;
    Button TimeSettingPageBackButton;

    RelativeLayout settingLayout;

    Button selectDate;
    TextView textView1;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    DialogFragment dialogFragment;
    String deadLine, today;

    int interval;

    final String notChanged = "There's no Deadline.. (Click above button)";
    final String emptyTitle = "";
    final String emptyContent = "";

    public static final int REQUEST_CODE = 11;

    NumberPicker np1,np2,np3;
    final String[] values = {"하루 2~3회", "하루 1~2회", "주 6~7회", "주 3회 미만"};
    final int[] intervals = {3,5,12,24};

    public MemoTimeSettingFragment() {}
    public static MemoTimeSettingFragment newInstance(){
        MemoTimeSettingFragment memoTimeSettingFragment = new MemoTimeSettingFragment();
        return memoTimeSettingFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.memo_time_setting,container,false);

        super.onCreateView(inflater,container,savedInstanceState);
        init(rootView);
        hideKeyboard();
        clickHideKeyboard();

        final FragmentManager fm = ((AppCompatActivity)getActivity()).getSupportFragmentManager();

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDialogFragment newFragment = new SelectDateFragment();
                newFragment.setTargetFragment(MemoTimeSettingFragment.this, REQUEST_CODE);
                newFragment.show(fm, "datePicker");
            }
        });

        //save button
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(deadLine == notChanged){
                    Toast.makeText(getContext(), "Set Deadline!!", Toast.LENGTH_SHORT).show();
                }
                else { saveData(); }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       // if(requestCode == REQUEST_CODE && requestCode == Activity.RESULT_OK){
            textView1.setText(data.getStringExtra("selectedDate"));
            deadLine = data.getStringExtra("selectedDate");
        //}
//        super.onActivityResult(requestCode, resultCode, data);
    }

    public void init(ViewGroup rootView ){

        MD = new MemoData();
        TimeSettingPageBackButton = rootView.findViewById(R.id.TimeSettingPageBackButton);
        settingLayout = rootView.findViewById(R.id.settingLayout);
        saveButton=(Button)rootView.findViewById(R.id.settingSaveButton);//저장버튼

        selectDate = (Button)rootView.findViewById(R.id.settingDate);
        textView1 = (TextView)rootView.findViewById(R.id.dateTextView);

        deadLine = notChanged;

        np1 = (NumberPicker) rootView.findViewById(R.id.np1);
        np2 = (NumberPicker) rootView.findViewById(R.id.np2);
        np3 = (NumberPicker) rootView.findViewById(R.id.np3);

        np1.setMinValue(0);
        np1.setMaxValue(values.length - 1);
        np1.setDisplayedValues(values);
        np1.setValue(0);

        np2.setMinValue(0);
        np2.setMaxValue(23);
        np2.setValue(23);

        np3.setMinValue(0);
        np3.setMaxValue(23);
        np3.setValue(9);

        mFormat=new SimpleDateFormat("yy-MM-dd hh:mm");//날짜 형식 지정

        //메모 받기
        mText=emptyTitle;
        mTitle=emptyContent;

        if(getArguments()!=null) {
            mTitle = getArguments().getString("title");
            mText = getArguments().getString("content");
        }
        clickBack();
    }

    //내부저장소 저장기능
    public void saveData(){

        //현재 시간 받기
        mNow=System.currentTimeMillis();
        mDate=new Date(mNow);
        time=mFormat.format(mDate);

        MD.setEndDateTextView(deadLine);
        MD.setRegistDateTextView(time);

        MD.setMemoText(mText);
        MD.setMemoTitle(mTitle);

        np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected value from picker
                interval = newVal;
            }
        });

        MD.setMinTime(Integer.toString(makeInterval(interval)));
        MD.setSleepStartTime(Integer.toString(np2.getValue()));
        MD.setSleepEndTime(Integer.toString(np3.getValue()));

        mainActivity.getReposityDB().insertMemo(MD);

        changeFragment(0);
    }

    public int makeInterval(int idx){
        return intervals[idx];
    }

    //프래그먼트 변환
    public void changeFragment(int idx){
        mainActivity.OnFragmentChange(0, null);//이 페이지에서 데이터 처리하고 널을 넘기자.
    }

    private void hideKeyboard(){
        mainActivity.getHideKeyboard().hideKeyboard();
    }
    private void clickHideKeyboard(){
        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
            }
        });
    }
    private void clickBack(){
        TimeSettingPageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.OnFragmentChange(1, null);
            }
        });
    }
}