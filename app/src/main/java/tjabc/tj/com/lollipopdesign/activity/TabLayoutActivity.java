package tjabc.tj.com.lollipopdesign.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import tjabc.tj.com.lollipopdesign.R;
import tjabc.tj.com.lollipopdesign.tablayout.PageFragment;
import tjabc.tj.com.lollipopdesign.tablayout.PageThreeFragment;
import tjabc.tj.com.lollipopdesign.tablayout.PageTwoFragment;
import tjabc.tj.com.lollipopdesign.tablayout.TabViewPagerAdapter;

public class TabLayoutActivity extends FragmentActivity {

    @Bind(R.id.tabLayout) TabLayout tabLayout;
    @Bind(R.id.viewPager) ViewPager viewPager;

    private ArrayList<String> fragArr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        ButterKnife.bind(this);

        initFragment();

        TabViewPagerAdapter adapter=new TabViewPagerAdapter(this,getSupportFragmentManager(),fragArr);
        viewPager.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Tab One"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab Two"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab Three"));

        tabLayout.setupWithViewPager(viewPager);
    }

    private void initFragment(){
        fragArr=new ArrayList<String>();
        PageFragment page1=new PageFragment();
        PageTwoFragment page2=new PageTwoFragment();
        PageThreeFragment page3=new PageThreeFragment();

        fragArr.add(page1.getClass().getName());
        fragArr.add(page2.getClass().getName());
        fragArr.add(page3.getClass().getName());
        fragArr.add(page1.getClass().getName());
    }
}
