package tjabc.tj.com.lollipopdesign.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import tjabc.tj.com.lollipopdesign.R;
import tjabc.tj.com.lollipopdesign.adapter.AppBarAdapter;
import tjabc.tj.com.lollipopdesign.adapter.ApplicationAdapter;
import tjabc.tj.com.lollipopdesign.entity.AppInfo;
import tjabc.tj.com.lollipopdesign.itemanimator.CustomItemAnimator;

public class AppBarActivity extends Activity {

    @Bind(R.id.appRecycler) RecyclerView appRecycler;
    @Bind(R.id.progressBar) ProgressBar progressBar;
    @Bind(R.id.toolbar) Toolbar toolbar;

    private AppBarAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar);
        ButterKnife.bind(this);

//        getLayoutInflater().inflate(R.layout.toobar_button, toolbar);
//        设置toolbar自定义布局

        isFinishState(false);

        appRecycler.setLayoutManager(new LinearLayoutManager(this));
        appRecycler.setItemAnimator(new CustomItemAnimator());
        mAdapter = new AppBarAdapter(this,initData());
        appRecycler.setAdapter(mAdapter);

        isFinishState(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AppBarActivity.this,TitleBarSynthActivity.class);
                startActivity(intent);
            }
        });
    }

    private ArrayList<String> initData() {
        ArrayList<String> infoArr = new ArrayList<String>();
        int i = 0;
        while (i < 8) {
            infoArr.add("我想有个她");
            infoArr.add("白天么么哒");
            infoArr.add("晚上啪啪啪");
            infoArr.add("上班啪啪啪");
            infoArr.add("吃饭啪啪啪");
            i++;
        }
        return infoArr;
    }

    private void isFinishState(boolean isFinish){
        if(isFinish){
            appRecycler.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }else{
            appRecycler.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }
}
