package com.relengxing.rxbus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import rx.Subscription;
import rx.functions.Action1;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {


    private TextView tv_normal;
    private ToggleButton btn_normal;
    private TextView tv_sticky;
    private ToggleButton btn_sticky;


    Subscription rxSubscription;
    Subscription rxSubscription2;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tv_normal = (TextView) view.findViewById(R.id.tv_normal);
        btn_normal = (ToggleButton) view.findViewById(R.id.btn_normal);
        tv_sticky = (TextView) view.findViewById(R.id.tv_sticky);
        btn_sticky = (ToggleButton) view.findViewById(R.id.btn_sticky);

        btn_normal.setOnCheckedChangeListener(this);
        btn_sticky.setOnCheckedChangeListener(this);
    }

    public void subscribe() {
        rxSubscription = RxBus.getDefault().toObservable(Integer.class).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                tv_normal.setText(tv_normal.getText() + "" + integer + "、");
            }
        });
    }

    public void subscribeSticky(){
//        rxSubscription2 = RxBus.getDefault().toObservableSticky(Integer.class).subscribe(new Action1<Integer>() {
//            @Override
//            public void call(Integer integer) {
//                tv_sticky.setText(tv_sticky.getText() + "" + integer + "、");
        rxSubscription2 = RxBus.getDefault().toObservableStickyAndRemove(Integer.class).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                tv_sticky.setText(tv_sticky.getText() + "" + integer + "、");
            }
        });
    }
    public void unsubscribe() {
        if (!rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
    }

    public void unsubscribeSticky(){
        if (!rxSubscription2.isUnsubscribed()){
            rxSubscription2.unsubscribe();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton == btn_normal){
            if (b){
                subscribe();
            }else {
                unsubscribe();
            }
        }
        if (compoundButton == btn_sticky){
            if (b){
                subscribeSticky();
            }else {
                unsubscribeSticky();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //此处如果rxSubscription从来没有绑定过会是null,此时执行unsubscribe();会报空指针异常
        if (rxSubscription != null){
            unsubscribe();
        }
        if (rxSubscription2 != null){
            unsubscribeSticky();
        }
    }
}
