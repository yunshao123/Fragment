package com.example.lan.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
    @BindView(R.id.h_progressbar)
    HorizontalProgressBarWithNumber hProgressbar;

    int progress=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        List<Fragment> fragments = new ArrayList<Fragment>();

        for (int i = 0; i < 3; i++) {
            FragmentOne fragment1 = new FragmentOne();
            fragments.add(fragment1);
        }
        PagerAdapter adapter = new MyAdater(getSupportFragmentManager(),
                fragments);
        vp.setAdapter(adapter);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MessageEvent(vp.getCurrentItem()));
            }
        });
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.e("到哪个页面了==", i + "");
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        RxTimerUtil.interval(2000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                hProgressbar.setProgress(++progress);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> list=new ArrayList<>();
                for (int i = 0; i <10 ; i++) {
                    list.add(i+"");
                }
                DialogItemAdapter adapter = new DialogItemAdapter(MainActivity.this ,list);

                AlertDialog alertDialog = new AlertDialog
                        .Builder(MainActivity.this)
                        .setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //ToastUtil.showToastShort("信息：");
                                dialog.dismiss();
                            }
                        }).create();

                alertDialog.show();
                //https://blog.csdn.net/u012246458/article/details/82835085
            }
        });
    }

    public void checkBox(){
        final String[] item = {"a", "b", "c"};
        final boolean[] selected = {true, false, true};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("多选列表");
        builder.setMultiChoiceItems(item, selected, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                Toast.makeText(MainActivity.this, item[which] + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < selected.length; i++) {
                    Log.e("hongliang", "" + selected[i]);
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
