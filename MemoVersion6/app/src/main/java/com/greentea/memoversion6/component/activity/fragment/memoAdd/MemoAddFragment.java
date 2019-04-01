package com.greentea.memoversion6.component.activity.fragment.memoAdd;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.greentea.memoversion6.R;
import com.greentea.memoversion6.component.activity.MainActivity;
import com.greentea.memoversion6.component.activity.fragment.memoTimeSetting.MemoTimeSettingFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import dagger.android.support.AndroidSupportInjection;


public class MemoAddFragment extends Fragment implements MainActivity.onKeyBackPressedListener{
    Button btn, AddPagebackButton;
    EditText titleData, contentData;
    LinearLayout memoAddLayout;
    MainActivity mainActivity;
    MemoTimeSettingFragment memoTimeSettingFragment;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).setOnKeyBackPressedListener(this);
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.configureDagger();
    }
    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){

        View view = inflater.inflate(R.layout.memo_add, container, false);
        init(view);
        //hideKeyboard();
        btn = view.findViewById(R.id.btn1);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(titleData.getText().toString().length() == 0 || contentData.getText().toString().length() == 0){
                    Toast.makeText(getContext(), "No title(content)!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", titleData.getText().toString());
                    bundle.putString("content", contentData.getText().toString());

                    FragmentTransaction fragmentTransaction = MainActivity.mainActivity.getSupportFragmentManager().beginTransaction();
                    memoTimeSettingFragment = new MemoTimeSettingFragment();
                    memoTimeSettingFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.mainContainer, memoTimeSettingFragment, null);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
        clickHideKeyboard();
        clickBack();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        titleData.setText("");
//        contentData.setText("");
    }

    public void init(View view) {
        titleData =  view.findViewById(R.id.title);
        contentData =  view.findViewById(R.id.content);
        memoAddLayout = view.findViewById(R.id.memoAddLayout);
        AddPagebackButton = view.findViewById(R.id.AddPagebackButton);
        titleData.setText("");
        contentData.setText("");
    }

    private void clickBack(){
        AddPagebackButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackKey();
            }
        });
    }
    private void hideKeyboard(){
        MainActivity.mainActivity.getHideKeyboard().hideKeyboard();
    }
    private void clickHideKeyboard(){
        memoAddLayout.setOnClickListener(new OnClickListener() {
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