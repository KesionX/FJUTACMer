package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import fg.MyProblemFragment;

/**
 * Created by Administrator on 2016/10/1.
 */
public class MyProblemFgAdapter extends FragmentPagerAdapter {
    private ArrayList<MyProblemFragment> list;

    public MyProblemFgAdapter(FragmentManager fm,ArrayList<MyProblemFragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
