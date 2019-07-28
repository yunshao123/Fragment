package com.example.lan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentOne extends Fragment {
    private int page=0;
    @BindView(R.id.tvshow)
    TextView tvshow;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, null);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        Log.e("进入fragment---》",page+"");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(MessageEvent message) {
        if (message.getMessage()==0){
            tvshow.setText("111");
        }else if(message.getMessage()==1){
            tvshow.setText("222");
        }else{
            tvshow.setText("333");
        }
    }

    public void addData(int i){
        page=i;
        Log.e("我是第=====", i+"" );
    }
}
