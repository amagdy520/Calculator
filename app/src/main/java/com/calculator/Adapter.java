package com.calculator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


/**
 * Created by Ahmed Magdy on 2/8/2018.
 */


public class Adapter extends FragmentPagerAdapter {
    private final String title[] = new String[]{"Main","Other"};
    private final Fragment fragment[] = new Fragment[title.length];
    public Adapter(FragmentManager fm) {
        super(fm);
        fragment[0] = new FragmentMain();
        fragment[1] = new FragmentOther();
    }
    @Override
    public Fragment getItem(int position) {
        Log.v("Title - get Item = ", String.valueOf(position));
        return fragment[position];
    }

    @Override
    public int getCount() {
        return fragment.length;
    }
}
