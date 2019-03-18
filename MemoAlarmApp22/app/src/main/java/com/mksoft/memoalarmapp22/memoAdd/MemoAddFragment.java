package com.mksoft.memoalarmapp22.memoAdd;

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

//import android.app.Fragment;
import com.mksoft.memoalarmapp22.MainActivity;
import com.mksoft.memoalarmapp22.R;

import androidx.fragment.app.Fragment;

public class MemoAddFragment extends Fragment {
    Button btn;
    Button AddPagebackButton;
    EditText titleData;
    EditText contentData;
    MainActivity mainActivity;
    LinearLayout memoAddLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)getActivity();
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){

        View view = inflater.inflate(R.layout.memo_add, container, false);
        titleData =  view.findViewById(R.id.title);
        contentData =  view.findViewById(R.id.content);
        memoAddLayout = view.findViewById(R.id.memoAddLayout);
        AddPagebackButton = view.findViewById(R.id.AddPagebackButton);
        hideKeyboard();
        init();
        btn = view.findViewById(R.id.btn1);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                if(titleData.getText().toString() == ""){
                    Toast.makeText(getContext(), "Please set title!", Toast.LENGTH_SHORT).show();
                }
                else if(contentData.getText().toString() == ""){
                    Toast.makeText(getContext(), "Please set memo!", Toast.LENGTH_SHORT).show();
                }
                else {
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

    public void init(){
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
        mainActivity.getHideKeyboard().hideKeyboard();
    }
    private void clickHideKeyboard(){
        memoAddLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();

            }
        });
    }

//    @Override
//    public void onAttach(Context context){
//        super.onAttach(context);
//        memoSetListener = (MemoSetListener) context;
//    }
//
//    @Override
//    public void onDetach(){
//        super.onDetach();
//        memoSetListener = null;
//    }

    public static MemoAddFragment newInstance() {
        return new MemoAddFragment();
    }
//    Button nextBtn;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//        return inflater.inflate(R.layout.memo_add, container, false);
//    }
//
//    @Override
//    public void onResume(){
//        nextBtn = (Button) this.getActivity().findViewById(R.id.btn1);
//        nextBtn.setOnClickListener(onClickListener);
//        super.onResume();
//    }
//
//    public interface  CustomOnClickListener{
//        public void onClicked(int id);
//    }



//
//    private CustomOnClickListener customListener;
//
//    OnClickListener onClickListener = new OnClickListener() {
//        @Override
//        public void onClick(View v) {
//           customListener.onClicked(v.getId());
//        }
//    };
//
//    @Override
//    public void onAttach(Activity activity){
//        super.onAttach(activity);
//        customListener = (CustomOnClickListener)activity;
//    }

//    Bundle bundle = new Bundle();
//    //Fragment fragment = new ViewActivity();
//
//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
//
//        View view = inflater.inflate(R.layout.memo_add, container, false);
//        EditText titleData = (EditText) view.findViewById(R.id.title);
//        EditText contentData = (EditText) view.findViewById(R.id.content);
//
//        bundle.putString("title", titleData.toString());
//        bundle.putString("content", contentData.toString());
//
//        Button btn = (Button) view.findViewById(R.id.btn1);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        return inflater.inflate(R.layout.memo_add, container, false);
//    }

}
