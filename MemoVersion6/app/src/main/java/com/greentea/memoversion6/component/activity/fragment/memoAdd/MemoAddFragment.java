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

import androidx.fragment.app.Fragment;
import dagger.android.support.AndroidSupportInjection;

//import android.App.Fragment;

public class MemoAddFragment extends Fragment {
    Button btn, AddPagebackButton;
    EditText titleData, contentData;
    LinearLayout memoAddLayout;
    MainActivity mainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)getActivity();
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

                if(titleData.getText().toString() == "" || contentData.getText().toString() == ""){
                    Toast.makeText(getContext(), "No title(content)!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", titleData.getText().toString());
                    bundle.putString("content", contentData.getText().toString());

                    mainActivity.OnFragmentChange(2, bundle);
                }
            }
        });
        clickHideKeyboard();
        clickBack();
        return view;
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
                mainActivity.OnFragmentChange(0,null);
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
}
