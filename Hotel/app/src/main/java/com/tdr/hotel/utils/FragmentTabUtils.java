package com.tdr.hotel.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.List;

/**
 * Author：Libin on 2019/6/6 13:26
 * Description：主界面 切换tab工具类
 */
public class FragmentTabUtils implements OnTabSelectListener {

    private List<Fragment> fragmentList; // 一个tab页面对应一个Fragment
    private CommonTabLayout tabLayout; // 用于切换tab
    private FragmentManager fragmentManager; // Fragment所属的Activity
    private int flContainerId; // Activity中当前fragment的区域的id

    /**
     * @param fragmentManager
     * @param fragmentList
     * @param flContainerId
     * @param tabLayout
     */
    public FragmentTabUtils(FragmentManager fragmentManager, List<Fragment> fragmentList,
                            int flContainerId, CommonTabLayout tabLayout) {
        this.fragmentList = fragmentList;
        this.tabLayout = tabLayout;
        this.fragmentManager = fragmentManager;
        this.flContainerId = flContainerId;
        initFragment();
        tabLayout.setOnTabSelectListener(this);
        setCurrentFragment(0);
    }


    private void initFragment() {
        //开启事务
        FragmentTransaction ft = fragmentManager.beginTransaction();
        //遍历集合
        for (int i = 0; i < fragmentList.size(); i++) {
            Fragment fragment = fragmentList.get(i);
            ft.add(flContainerId, fragment, String.valueOf(i));
        }
        //提交事务
        ft.commitAllowingStateLoss();
    }

    /**
     * 切换fragment
     *
     * @param position
     */
    private void switchFragment(int position) {
        //开启事务
        FragmentTransaction ft = fragmentManager.beginTransaction();
        for (int i = 0; i < fragmentList.size(); i++) {
            Fragment fragment = fragmentList.get(i);
            if (i == position) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
        }
        //提交事务
        ft.commitAllowingStateLoss();
    }


    /**
     * @param position 设置当前fragment
     */
    public void setCurrentFragment(int position) {
        tabLayout.setCurrentTab(position);
        switchFragment(position);
    }

    @Override
    public void onTabSelect(int position) {
        switchFragment(position);
    }

    @Override
    public void onTabReselect(int position) {

    }
}
