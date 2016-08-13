package com.relengxing.rxbus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LastFragment extends Fragment implements View.OnClickListener {

    private int normalCount = 0;
    private int stickyCount = 0;
    private TextView tv_normal;
    private Button btn_send_normal;
    private TextView tv_sticky;
    private Button btn_send_sticky;

    public LastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_last, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tv_normal = (TextView) view.findViewById(R.id.tv_normal);
        btn_send_normal = (Button) view.findViewById(R.id.btn_send_normal);
        tv_sticky = (TextView) view.findViewById(R.id.tv_sticky);
        btn_send_sticky = (Button) view.findViewById(R.id.btn_send_sticky);

        btn_send_normal.setOnClickListener(this);
        btn_send_sticky.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_normal:
                RxBus.getDefault().post(normalCount);
                tv_normal.setText(tv_normal.getText()+""+normalCount+"、");
                normalCount++;
                break;
            case R.id.btn_send_sticky:
                RxBus.getDefault().postSticky(stickyCount);
//                RxBus.getDefault().postStickyAndCover(stickyCount);
                tv_sticky.setText(tv_sticky.getText()+""+stickyCount+"、");
                stickyCount++;
                break;
        }
    }
}
