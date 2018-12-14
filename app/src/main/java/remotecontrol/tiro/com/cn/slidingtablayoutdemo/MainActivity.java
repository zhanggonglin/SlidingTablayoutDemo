package remotecontrol.tiro.com.cn.slidingtablayoutdemo;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import remotecontrol.tiro.com.cn.slidingtablayoutdemo.view.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablayout)
    SlidingTabLayout tablayout;

    private List<String> strings = new ArrayList<String>();
    private List<Fragment> fragments = new ArrayList<>();
    private String[] tabTitles = {"公开课", "精选课", "免费课"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragments();
        initView();
    }

    private void initView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        TabFragmentProAdapter tabFragmentProAdapter = new TabFragmentProAdapter(fragments, strings, fragmentManager, MainActivity.this);
        viewpager.setAdapter(tabFragmentProAdapter);
        tablayout.setupWithViewPager(viewpager);
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tv_tab_title).setSelected(true);
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tablayout.redrawIndicator(position, positionOffset);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        for (int i = 0; i < tablayout.getTabCount(); i++) {
            TabLayout.Tab tab = tablayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(getTabView(i));
                if (i == 0) {
                    // 设置第一个tab的TextView是被选择的样式
                    tab.getCustomView().findViewById(R.id.tv_tab_title).setSelected(true);//第一个tab被选中
                }
                if (tab.getCustomView() != null) {
                    View tabView = (View) tab.getCustomView().getParent();
                    tabView.setTag(i);
                }
            }
        }
    }

    private void initFragments() {
        PurchasedAllFragment orderAllFragment = new PurchasedAllFragment();
        PurchasedNonPayFragment orderNonPayFragment = new PurchasedNonPayFragment();
        PurchasedPaidFragment orderPaidFragment = new PurchasedPaidFragment();
        fragments.clear();
        strings.clear();
        fragments.add(orderAllFragment);
        fragments.add(orderNonPayFragment);
        fragments.add(orderPaidFragment);
        for (String tabTitle : tabTitles) {
            strings.add(tabTitle);
        }
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_tab_item, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_tab_title);
        try {
            tv.setText(tabTitles[position]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
