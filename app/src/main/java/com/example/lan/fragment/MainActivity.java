package com.example.lan.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.vp)
    ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        List<Fragment> fragments = new ArrayList<Fragment>();

        for (int i = 0; i <3 ; i++) {
            FragmentOne fragment1 = new FragmentOne();
            fragments.add(fragment1);
            fragment1.addData(i);
        }
        PagerAdapter adapter = new MyAdater(getSupportFragmentManager(),
                fragments);
        vp.setAdapter(adapter);
        Log.e("这是第几页===",vp.getCurrentItem()+"");
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("这是第几页===",vp.getCurrentItem()+"");
                EventBus.getDefault().post(new MessageEvent(vp.getCurrentItem()));
            }
        });
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
              Log.e("到哪个页面了==",i+"");
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
